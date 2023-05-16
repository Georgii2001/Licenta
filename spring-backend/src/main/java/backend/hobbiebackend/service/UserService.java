package backend.hobbiebackend.service;

import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.UpdateAppClientDto;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity register(AppClientSignUpDto userDTO);

    void updatedUserEntity(UpdateAppClientDto appClient);

    UserEntity findUserById(Integer userId);

    UserEntity findUserByEmail(String email);

    UsersDTO getUserMainDetails(String username, Integer id);

    boolean userExists(String username, String email);

    void saveUserWithUpdatedPassword(UserEntity userEntity);

    List<UsersDTO> getAllUsersMatchesForClient(String username);

    List<UsersDTO> getAllUsersMatchesForClient(String username, Integer page);
}


