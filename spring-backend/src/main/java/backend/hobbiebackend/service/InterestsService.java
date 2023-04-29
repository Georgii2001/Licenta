package backend.hobbiebackend.service;

import java.util.List;

public interface InterestsService {

    List<String> getUnassignedInterests(String username);

    void saveNewUserInterest(String username, List<String> interestsList);

    void removeCurrentUserInterest(String username, String interest);
}
