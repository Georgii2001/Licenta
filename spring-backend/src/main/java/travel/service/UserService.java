package travel.service;

import travel.dto.AppClientSignUpDto;
import travel.dto.EmailResponseDTO;
import travel.dto.UpdateAppClientDto;
import travel.dto.UsersDTO;
import travel.entities.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity register(AppClientSignUpDto userDTO);

    void updatedUserEntity(UpdateAppClientDto appClient);

    UserEntity findUserById(Integer userId);

    EmailResponseDTO sendEmailInvitation(String username, Integer receiverId);
    UserEntity sendPasswordEmail(String email);

    UsersDTO getUserMainDetails(String username, Integer id);

    boolean userExists(String username, String email);

    void saveUserWithUpdatedPassword(UserEntity userEntity);

    List<UsersDTO> getAllUsersMatchesForClient(String username);

    List<UsersDTO> getAllUsersMatchesForClient(String username, Integer page);
}


