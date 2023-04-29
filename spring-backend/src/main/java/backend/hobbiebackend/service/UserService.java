package backend.hobbiebackend.service;

import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.BusinessRegisterDto;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.AppClient;
import backend.hobbiebackend.entities.BusinessOwner;
import backend.hobbiebackend.entities.Hobby;
import backend.hobbiebackend.entities.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> seedUsersAndUserRoles();

    UserEntity register(AppClientSignUpDto userDTO);

    BusinessOwner registerBusiness(BusinessRegisterDto business);

    BusinessOwner saveUpdatedUser(BusinessOwner businessOwner);

    AppClient saveUpdatedUserClient(AppClient appClient);

    UserEntity findUserById(Integer userId);

    UserEntity findUserByEmail(String email);

    boolean deleteUser(Integer id);

    BusinessOwner findBusinessOwnerById(Integer id);

    UsersDTO getUserMainDetails(String username, Integer id);

    boolean userExists(String username, String email);

    void saveUserWithUpdatedPassword(UserEntity userEntity);

    AppClient findAppClientById(Integer clientId);

    void findAndRemoveHobbyFromClientsRecords(Hobby hobby);

    boolean businessExists(String businessName);

    AppClient findAppClientByUsername(String username);

    BusinessOwner findBusinessByUsername(String username);

    List<UsersDTO> getAllUsersMatchesForClient(String username);

    List<UsersDTO> getAllUsersMatchesForClient(String username, Integer page);
}


