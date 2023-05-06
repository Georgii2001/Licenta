package backend.hobbiebackend.service;

import backend.hobbiebackend.dto.UsersDTO;

import java.util.List;

public interface MatchesService {

    List<UsersDTO> getAllUsersMatchesForClient(String username);
}
