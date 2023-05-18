package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.AvatarsDTO;
import backend.hobbiebackend.dto.UpdateAppClientDto;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.enums.GenderEnum;
import backend.hobbiebackend.enums.UserRoleEnum;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.mapper.UserMapper;
import backend.hobbiebackend.repostiory.UserInterestsRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.service.UserService;
import backend.hobbiebackend.utils.PhotoUtils;
import backend.hobbiebackend.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PhotoUtils photoUtils;
    private final UserInterestsRepository userInterestsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;

    @Override
    public UserEntity register(AppClientSignUpDto userDTO) {
        UserEntity user = this.modelMapper.map(userDTO, UserEntity.class);
        user.setRole(UserRoleEnum.USER.name());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        final String avatarName = userDTO.getAvatar().getOriginalFilename();
        user.setAvatar(avatarName);
        userRepository.saveAndFlush(user);

        user = userRepository.getOne(user.getId());
        final String finalAvatarName = photoUtils.saveAvatarInDB(user, avatarName, 0);
        user.setAvatar(finalAvatarName);
        userRepository.saveAndFlush(user);

        photoUtils.savePhoto(userDTO.getAvatar(), userDTO.getUsername(), finalAvatarName);

        return user;
    }

    @Override
    public void updatedUserEntity(UpdateAppClientDto user) {
        UserEntity userEntity = userUtils.getUserEntity(null, null, user.getEmail());

        final String username = user.getFullName();
        if (username != null && !username.isEmpty()) {
            userEntity.setUsername(username);
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
    public UserEntity findUserByEmail(String email) {
        Optional<UserEntity> byEmail = this.userRepository.findByEmail(email);
        return byEmail.orElse(null);
    }

    @Override
    public UsersDTO getUserMainDetails(String username, Integer id) {
        UserEntity user = userUtils.getUserEntity(username, id, null);
        final String mainAvatar = photoUtils.getEncodedFile(user.getAvatar(), username);
        List<String> userInterests = getUserInterests(user);
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

    public List<String> getUserInterests(UserEntity user) {
        return userInterestsRepository.findByUserEntityId(user.getId()).stream()
                .map(userInterest -> userInterest.getInterests().getInterestName())
                .collect(Collectors.toList());
    }
}