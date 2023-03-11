package backend.hobbiebackend.service;

import backend.hobbiebackend.entities.AppClient;
import backend.hobbiebackend.entities.Hobby;

import java.util.List;
import java.util.Set;

public interface HobbyService {
    Hobby findHobbieById(Integer id);

    void saveUpdatedHobby(Hobby hobby) throws Exception;

    boolean deleteHobby(int id) throws Exception;

    Set<Hobby> findHobbyMatches(String username);

    boolean saveHobbyForClient(Hobby hobby, String username);

    boolean removeHobbyForClient(Hobby hobby, String username);

    boolean isHobbySaved(Integer hobbyId, String username);

    List<Hobby> findSavedHobbies(AppClient appClient);

    Set<Hobby> getAllHobbiesForBusiness(String username);
    
    Set<Hobby> getAllHobbieMatchesForClient(String username);

    void createHobby(Hobby offer);
}
