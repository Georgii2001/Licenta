package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.BusinessRegisterDto;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.AppClient;
import backend.hobbiebackend.entities.BusinessOwner;
import backend.hobbiebackend.entities.Hobby;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.enums.GenderEnum;
import backend.hobbiebackend.enums.UserRoleEnum;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.mapper.UserMapper;
import backend.hobbiebackend.repostiory.AppClientRepository;
import backend.hobbiebackend.repostiory.BusinessOwnerRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.service.UserService;
import backend.hobbiebackend.utils.PhotoUtils;
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
    private final AppClientRepository appClientRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> seedUsersAndUserRoles() {
        List<UserEntity> seededUsers = new ArrayList<>();
        //simple user
        if (appClientRepository.count() == 0) {
            UserEntity user = new UserEntity();
            user.setUsername("user");
            user.setEmail("n13@gmail.com");
            user.setPassword(passwordEncoder.encode("topsecret"));
            user.setRole(UserRoleEnum.USER.name());
            user.setGender(GenderEnum.FEMALE.name());

            userRepository.save(user);
            seededUsers.add(user);


        }
        if (businessOwnerRepository.count() == 0) {
            //business_user
            BusinessOwner business_user = new BusinessOwner();
            business_user.setUsername("business");
            business_user.setEmail("n10@gamil.com");
            business_user.setPassword(this.passwordEncoder.encode("topsecret"));
            business_user.setRole(UserRoleEnum.USER.name());
            business_user.setBusinessName("My Business name");
            business_user.setAddress("My business address");
            businessOwnerRepository.save(business_user);
            seededUsers.add(business_user);
        }
        return seededUsers;
    }

    @Override
    public UserEntity register(AppClientSignUpDto userDTO) {
        UserEntity user = this.modelMapper.map(userDTO, UserEntity.class);
        user.setRole(UserRoleEnum.USER.name());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        photoUtils.savePhoto(userDTO.getAvatar(), userDTO.getUsername());
        user.setAvatar(userDTO.getAvatar().getOriginalFilename());
        return userRepository.save(user);
    }

    @Override
    public BusinessOwner registerBusiness(BusinessRegisterDto business) {
        BusinessOwner businessOwner = this.modelMapper.map(business, BusinessOwner.class);
        businessOwner.setRole(UserRoleEnum.USER.name());
        businessOwner.setPassword(this.passwordEncoder.encode(business.getPassword()));
        return businessOwnerRepository.save(businessOwner);
    }

    @Override
    public BusinessOwner saveUpdatedUser(BusinessOwner businessOwner) {
        return this.businessOwnerRepository.save(businessOwner);
    }

    @Override
    public AppClient saveUpdatedUserClient(AppClient appClient) {
        return this.appClientRepository.save(appClient);
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
        if (byEmail.isPresent()) {
            return byEmail.get();
        } else {
            return null;
        }
    }

    @Override
    public BusinessOwner findBusinessOwnerById(Integer id) {
        Optional<BusinessOwner> businessOwner = this.businessOwnerRepository.findById(id);
        if (businessOwner.isPresent()) {
            return businessOwner.get();
        } else {
            throw new NotFoundException("Can not find business owner");
        }
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        } else {
            throw new NotFoundException("Can not find user with this username");
        }
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
    public boolean deleteUser(Integer id) {
        UserEntity user = findUserById(id);
        if (user == null) {
            return false;
        }
        Optional<BusinessOwner> byId = this.businessOwnerRepository.findById(user.getId());

//        if (byId.isPresent()) {
//            List<AppClient> all = appClientRepository.findAll();
//            for (AppClient client : all) {
//                for (Hobby hobby : byId.get().getHobby_offers()) {
//                    client.getHobby_matches().remove(hobby);
//                    client.getSaved_hobbies().remove(hobby);
//                }
//                this.userRepository.save(client);
//            }
//        }
        userRepository.delete(user);
        return true;
    }


    @Override
    public AppClient findAppClientById(Integer clientId) {
        Optional<AppClient> user = this.appClientRepository.findById(clientId);
        if (user.isPresent()) {

            return user.get();
        } else {
            throw new NotFoundException("Can not find current user.");
        }
    }

    @Override
    public void findAndRemoveHobbyFromClientsRecords(Hobby hobby) {
        List<AppClient> all = this.appClientRepository.findAll();

//        for (AppClient appClient : all) {
//            appClient.getSaved_hobbies().remove(hobby);
//            appClient.getHobby_matches().remove(hobby);
//        }
    }


    @Override
    public boolean businessExists(String businessName) {
        Optional<BusinessOwner> byBusinessName = this.businessOwnerRepository.findByBusinessName(businessName);
        return byBusinessName.isPresent();
    }

    @Override
    public AppClient findAppClientByUsername(String username) {
        return this.appClientRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public BusinessOwner findBusinessByUsername(String username) {
        return this.businessOwnerRepository.findByUsername(username).get();
    }

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username) {
        List<UsersDTO> usersDTO = new ArrayList<>(Collections.emptyList());
        userRepository.findByRole(UserRoleEnum.USER.name()).stream()
                .filter(user -> !user.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList())
                .forEach(user -> {
                    final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
                    usersDTO.add(userMapper.mapUserToDTO(user, avatar));
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
                    usersDTO.add(userMapper.mapUserToDTO(user, avatar));
                });

        return usersDTO;
    }
}