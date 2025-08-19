package travel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.dto.*;
import travel.email.models.EmailRequest;
import travel.email.utils.EmailUtils;
import travel.entities.UserEntity;
import travel.enums.GenderEnum;
import travel.enums.UserRoleEnum;
import travel.handler.NotFoundException;
import travel.mapper.UserMapper;
import travel.repostiory.UserRepository;
import travel.service.UserService;
import travel.utils.PhotoUtils;
import travel.utils.UserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static travel.email.constants.TemplateNames.INVITE_TO_NEW_TRIP;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PhotoUtils photoUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;
    private final EmailUtils emailUtils;
    private final JavaMailSender javaMailSender;

    @Override
    public UserEntity register(AppClientSignUpDto userDTO) {
        log.info("Registering user with username: {}", userDTO.getUsername());

        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        user.setRole(UserRoleEnum.USER.name());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        final String avatarName = userDTO.getAvatar().getOriginalFilename();
        user.setAvatar(avatarName);
        userRepository.save(user);

        user = userUtils.getUserEntity(null, user.getId(), null);
        final String finalAvatarName = photoUtils.saveAvatarInDB(user, avatarName, 0);
        user.setAvatar(finalAvatarName);

        log.debug("Avatar saved with name: {}", finalAvatarName);
        userRepository.save(user);

        photoUtils.savePhoto(userDTO.getAvatar(), userDTO.getUsername(), finalAvatarName);

        return user;
    }

    @Override
    public void updatedUserEntity(UpdateAppClientDto user) {
        log.info("Updating user with email: {}", user.getEmail());
        UserEntity userEntity = userUtils.getUserEntity(null, null, user.getEmail());

        final String username = user.getDisplayName();
        if (username != null && !username.isEmpty()) {
            userEntity.setDisplayName(username);
        }

        final GenderEnum gender = user.getGender();
        if (gender != null) {
            userEntity.setGender(gender.name());
        }

        final String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(password));
        }


        final String description = user.getDescription();
        if (description != null && !description.isEmpty()) {
            userEntity.setDescription(description);
        }

        log.debug("Updating avatar for user: {}", userEntity.getUsername());
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserById(Integer userId) {
        Optional<UserEntity> byId = this.userRepository.findById(userId);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public EmailResponseDTO sendEmailInvitation(String username, Integer receiverId) {
        log.info("Sending email invitation from user: {} to receiver ID: {}", username, receiverId);
        UserEntity senderUser = userUtils.getUserEntity(username, null, null);
        UserEntity receiverUser = userUtils.getUserEntity(null, receiverId, null);

        EmailRequest emailRequest = new EmailRequest();

        emailRequest.setSender(senderUser);
        emailRequest.setReceiver(receiverUser);

        log.debug("Preparing email request for invitation to new trip");
        return new EmailResponseDTO(emailUtils.notifyUser(emailRequest, INVITE_TO_NEW_TRIP.name()));
    }

    @Override
    public UserEntity sendPasswordEmail(String email) {
        log.info("Sending password reset email to: {}", email);
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        SimpleMailMessage mail = new SimpleMailMessage();
        String mailBody = "http://localhost:4200/password/" + user.get().getId();
        mail.setTo(user.get().getEmail());
        mail.setFrom("travel.with.me.enjoytheworld@gmail.com");
        mail.setSubject("Change your password");
        mail.setText("Click the link to reset your password: " + mailBody);

        log.debug("Sending password reset email to: {}", user.get().getEmail());
        javaMailSender.send(mail);
        return user.get();
    }

    @Override
    public UsersDTO getUserMainDetails(String username, Integer id) {
        log.info("Retrieving main details for user: {} with ID: {}", username, id);
        UserEntity user = userUtils.getUserEntity(username, id, null);
        final String mainAvatar = photoUtils.getEncodedFile(user.getAvatar(), username);
        List<String> userInterests = userUtils.getUserInterests(user);
        List<AvatarsDTO> userAvatars = photoUtils.getUserAvatars(user.getUsername(), user.getId());
        return userMapper.mapUserToDTO(user, mainAvatar, userAvatars, userInterests, null);
    }

    @Override
    public boolean userExists(String username, String email) {
        log.info("Checking if user exists with username: {} or email: {}", username, email);
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);

        if(byUsername.isPresent() || byEmail.isPresent()){
            log.warn("User exists with username: {} or email: {}", username, email);
        }

        return byUsername.isPresent() || byEmail.isPresent();
    }

    @Override
    public void saveUserWithUpdatedPassword(UserEntity userEntity) {
        log.info("Saving user with updated password for user ID: {}", userEntity.getId());
        this.userRepository.save(userEntity);
    }

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username) {
        log.info("Retrieving all user matches for client with username: {}", username);
        List<UsersDTO> usersDTO = new ArrayList<>(Collections.emptyList());

        userRepository.findByRole(UserRoleEnum.USER.name()).stream()
                .filter(user -> !user.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList())
                .forEach(user -> {
                    final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
                    usersDTO.add(userMapper.mapUserToDTO(user, avatar, null, null, null));
                });

        return usersDTO;
    }

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username, Integer page) {
        log.info("Retrieving paginated user matches for client with username: {} on page: {}", username, page);
        List<UsersDTO> usersDTO = new ArrayList<>(Collections.emptyList());
        Pageable pageable = PageRequest.of(page, 1);

        userRepository.findByRole(UserRoleEnum.USER.name(), username, pageable).toList()
                .forEach(user -> {
                    final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
                    usersDTO.add(userMapper.mapUserToDTO(user, avatar, null, null, null));
                });

        return usersDTO;
    }


}