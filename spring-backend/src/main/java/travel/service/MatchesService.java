package travel.service;

import travel.dto.EmailResponseDTO;
import travel.dto.UsersDTO;

import java.util.List;

public interface MatchesService {

    List<UsersDTO> getAllUsersMatchesForClient(String username);

    EmailResponseDTO addUserToMatches(String email, Integer matchedUserId, String status);

    List<UsersDTO> getAllMyMatches(String email);
}
