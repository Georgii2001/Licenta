package travel.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.*;
import java.util.stream.Collectors;

import static travel.email.constants.TemplateNames.INVITE_TO_NEW_TRIP;

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

    @Override
    public UserEntity register(AppClientSignUpDto userDTO) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        user.setRole(UserRoleEnum.USER.name());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        final String avatarName = userDTO.getAvatar().getOriginalFilename();
        user.setAvatar(avatarName);
        userRepository.save(user);

        user = userUtils.getUserEntity(null, user.getId(), null);
        final String finalAvatarName = photoUtils.saveAvatarInDB(user, avatarName, 0);
        user.setAvatar(finalAvatarName);
        userRepository.save(user);

        photoUtils.savePhoto(userDTO.getAvatar(), userDTO.getUsername(), finalAvatarName);

        return user;
    }

    @Override
    public void updatedUserEntity(UpdateAppClientDto user) {
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
        UserEntity senderUser = userUtils.getUserEntity(username, null, null);
        UserEntity receiverUser = userUtils.getUserEntity(null, receiverId, null);

        EmailRequest emailRequest = new EmailRequest();

        emailRequest.setSender(senderUser);
        emailRequest.setReceiver(receiverUser);

        return new EmailResponseDTO(emailUtils.notifyUser(emailRequest, INVITE_TO_NEW_TRIP.name()));
    }

    @Override
    public UsersDTO getUserMainDetails(String username, Integer id) {
        UserEntity user = userUtils.getUserEntity(username, id, null);
        final String mainAvatar = photoUtils.getEncodedFile(user.getAvatar(), username);
        List<String> userInterests = userUtils.getUserInterests(user);
        List<AvatarsDTO> userAvatars = photoUtils.getUserAvatars(user.getUsername(), user.getId());
        return userMapper.mapUserToDTO(user, mainAvatar, userAvatars, userInterests, null);
    }

    @Override
    public boolean userExists(String username, String email) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        return byUsername.isPresent() || byEmail.isPresent();
    }

    @Override
    public void saveUserWithUpdatedPassword(UserEntity userEntity) {
        this.userRepository.save(userEntity);
    }

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username) {
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