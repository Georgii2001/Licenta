package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.entities.Interests;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.entities.UserInterests;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.repostiory.InterestsRepository;
import backend.hobbiebackend.repostiory.UserInterestsRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.service.InterestsService;
import backend.hobbiebackend.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestsServiceImpl implements InterestsService {

    private final UserUtils userUtils;
    private final UserRepository userRepository;
    private final InterestsRepository interestsRepository;
    private final UserInterestsRepository userInterestsRepository;

    @Override
    public List<String> getUnassignedInterests(String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            UserEntity user = byUsername.get();
            List<Interests> interestsList = interestsRepository.findInterestAvailableForUser(user.getId());
            return interestsList.stream().map(Interests::getInterestName).collect(Collectors.toList());
        } else {
            throw new NotFoundException("Can not find user with this username");
        }
    }

    @Override
    public void saveNewUserInterest(String username, List<String> interestsList) {
        saveInterestByUserEntity(userUtils.getUserEntity(username, null, null), interestsList);
    }

    @Override
    public void discoverNewInterests(Integer userId, List<String> interestsList) {
        userInterestsRepository.deleteByUserEntityId(userId);
        saveInterestByUserEntity(userUtils.getUserEntity(null, userId, null), interestsList);
    }

    private void saveInterestByUserEntity(UserEntity userEntity, List<String> interestsList) {
        Map<String, Interests> availableInterestsList = interestsRepository.findInterestAvailableForUser(userEntity.getId())
                .stream().collect(Collectors.toMap(Interests::getInterestName, i -> i));

        List<UserInterests> userInterests = new ArrayList<>();
        interestsList.forEach(interest -> {
            if (availableInterestsList.containsKey(interest)) {
                UserInterests userInterest = UserInterests.builder()
                        .userEntity(userEntity)
                        .interests(availableInterestsList.get(interest))
                        .build();
                userInterests.add(userInterest);
            }
        });
        userInterestsRepository.saveAll(userInterests);
    }

    @Override
    public void removeCurrentUserInterest(String username, String interest) {
        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        userInterestsRepository.deleteUserInterest(userEntity.getId(), interest);
    }
}
