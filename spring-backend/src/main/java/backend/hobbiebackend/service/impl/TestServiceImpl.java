package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.entities.AppClient;
import backend.hobbiebackend.entities.Hobby;
import backend.hobbiebackend.entities.Test;
import backend.hobbiebackend.repostiory.TestRepository;
import backend.hobbiebackend.service.HobbyService;
import backend.hobbiebackend.service.TestService;
import backend.hobbiebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final UserService userService;
    private final HobbyService hobbyService;

    @Override
    public void saveTestResults(Test results) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(results.getUsername());
        if (currentUserAppClient.getTestResults() != null) {
            results.setId(currentUserAppClient.getTestResults().getId());
        }
        this.testRepository.save(results);
        currentUserAppClient.setTestResults(results);

        Set<Hobby> hobbyMatches = this.hobbyService.findHobbyMatches(currentUserAppClient.getUsername());
       // currentUserAppClient.setHobby_matches(hobbyMatches);
//        this.userService.updatedUserEntity(currentUserAppClient);
    }
}
