package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.model.dto.AppClientSignUpDto;
import backend.hobbiebackend.model.dto.BusinessRegisterDto;
import backend.hobbiebackend.model.entities.*;
import backend.hobbiebackend.model.entities.enums.GenderEnum;
import backend.hobbiebackend.model.entities.enums.UserRoleEnum;
import backend.hobbiebackend.model.repostiory.AppClientRepository;
import backend.hobbiebackend.model.repostiory.BusinessOwnerRepository;
import backend.hobbiebackend.model.repostiory.UserRepository;
import backend.hobbiebackend.service.UserRoleService;
import backend.hobbiebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AppClientRepository appClientRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> seedUsersAndUserRoles() {
        List<UserEntity> seededUsers = new ArrayList<>();
        //simple user
        if (appClientRepository.count() == 0) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRole(UserRoleEnum.USER);
            userRoleService.saveRole(userRoleEntity);
            UserRoleEntity userRoleEntity2 = new UserRoleEntity();
            userRoleEntity2.setRole(UserRoleEnum.ADMIN);
            userRoleService.saveRole(userRoleEntity2);
            UserEntity user = new AppClient();
            user.setUsername("user");
            user.setEmail("n13@gmail.com");
            user.setPassword(passwordEncoder.encode("topsecret"));
            user.setRole(UserRoleEnum.USER);
            user.setGender(GenderEnum.FEMALE);

            userRepository.save(user);
            seededUsers.add(user);


        }
        if (businessOwnerRepository.count() == 0) {
            UserRoleEntity userRoleEntity3 = new UserRoleEntity();
            userRoleEntity3.setRole(UserRoleEnum.BUSINESS_USER);
            userRoleService.saveRole(userRoleEntity3);
            //business_user
            BusinessOwner business_user = new BusinessOwner();
            business_user.setUsername("business");
            business_user.setEmail("n10@gamil.com");
            business_user.setPassword(this.passwordEncoder.encode("topsecret"));
            business_user.setRole(UserRoleEnum.USER);
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
        user.setRole(UserRoleEnum.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public BusinessOwner registerBusiness(BusinessRegisterDto business) {
        UserRoleEntity businessUserRole = this.userRoleService.getUserRoleByEnumName(UserRoleEnum.BUSINESS_USER);
        BusinessOwner businessOwner = this.modelMapper.map(business, BusinessOwner.class);
        businessOwner.setRole(UserRoleEnum.USER);
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
    public UserEntity findUserById(Long userId) {
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
    public BusinessOwner findBusinessOwnerById(Long id) {
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
    public boolean deleteUser(Long id) {
        UserEntity user = findUserById(id);
        if (user == null) {
            return false;
        }
        Optional<BusinessOwner> byId = this.businessOwnerRepository.findById(user.getId());

        if (byId.isPresent()) {
            List<AppClient> all = appClientRepository.findAll();
            for (AppClient client : all) {
                for (Hobby hobby : byId.get().getHobby_offers()) {
                    client.getHobby_matches().remove(hobby);
                    client.getSaved_hobbies().remove(hobby);
                }
                this.userRepository.save(client);
            }
        }
        userRepository.delete(user);
        return true;
    }


    @Override
    public AppClient findAppClientById(Long clientId) {
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

        for (AppClient appClient : all) {
            appClient.getSaved_hobbies().remove(hobby);
            appClient.getHobby_matches().remove(hobby);
        }
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
}
