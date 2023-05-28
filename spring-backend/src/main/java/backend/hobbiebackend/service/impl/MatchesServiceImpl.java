package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.dto.AvatarsDTO;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.MatchesStatus;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.entities.UsersMatches;
import backend.hobbiebackend.handler.AlreadyExistsException;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.mapper.UserMapper;
import backend.hobbiebackend.models.UserMatches;
import backend.hobbiebackend.repostiory.MatchesStatusRepository;
import backend.hobbiebackend.repostiory.UserInterestsRepository;
import backend.hobbiebackend.repostiory.UserMatchesRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.service.MatchesService;
import backend.hobbiebackend.utils.PhotoUtils;
import backend.hobbiebackend.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchesServiceImpl implements MatchesService {

    private final UserUtils userUtils;
    private final PhotoUtils photoUtils;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserInterestsRepository userInterestsRepository;
    private final MatchesStatusRepository matchesStatusRepository;
    private final UserMatchesRepository userMatchesRepository;

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username) {

        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        final Integer userId = userEntity.getId();

        List<UserMatches> userMatches = userInterestsRepository.countCommonInterestsByUserId(userId);

        Map<Integer, Long> userMatchesMap = userMatches.stream()
                .collect(Collectors.toMap(UserMatches::getUserId, UserMatches::getMatches));

        List<UsersMatches> usersMatches = userMatchesRepository.findByUserEquals(userEntity);
        List<Integer> matchedIds = usersMatches.stream()
                .distinct()
                .map(userMatch -> userMatch.getUserMatched().getId())
                .filter(i -> !Objects.equals(i, userId))
                .collect(Collectors.toList());

        return userRepository.findByIdNotIn(matchedIds).stream()
                .filter(user -> !user.getUsername().equalsIgnoreCase(username))
                .map(user -> {
                    final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
                    List<AvatarsDTO> userAvatars = photoUtils.getUserAvatars(user.getUsername(), user.getId());
                    List<String> userInterests = userUtils.getUserInterests(user);
                    return userMapper.mapUserToDTO(user, avatar, userAvatars, userInterests, userMatchesMap.getOrDefault(user.getId(), 0L));
                })
                .sorted(Comparator.comparing(UsersDTO::getUserMatchCount))
                .collect(Collectors.toList());
    }

    @Override
    public void addUserToMatches(String username, Integer matchedUserId, String status) {
        MatchesStatus matchesStatus = obtainStatusFromDB(status);

        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        List<UsersMatches> usersMatchesList = userMatchesRepository
                .findByUserIdEqualsAndUserMatchedIdEquals(userEntity.getId(), matchedUserId);

        if (usersMatchesList.isEmpty()) {
            UsersMatches usersMatches = new UsersMatches();
            UserEntity userMatchedEntity = userUtils.getUserEntity(null, matchedUserId, null);

            usersMatches.setUser(userEntity);
            usersMatches.setUserMatched(userMatchedEntity);
            usersMatches.setMatchesStatus(matchesStatus);

            userMatchesRepository.saveAndFlush(usersMatches);
        } else {
            throw new AlreadyExistsException("You can find this user on my matches page!!!");
        }
    }

    private MatchesStatus obtainStatusFromDB(String status) {
        MatchesStatus matchesStatus = matchesStatusRepository.findByMatchesStatusNameEquals(status);

        if (matchesStatus == null) {
            throw new NotFoundException("Sent matches status does not exists!!!");
        }
        return matchesStatus;
    }

    @Override
    public List<UsersDTO> getAllMyMatches(String email) {
        UserEntity userEntity = userUtils.getUserEntity(email, null, null);
        List<UsersMatches> usersMatches = userMatchesRepository.getAllMatchedUserMatches(userEntity);
        final Integer userId = userEntity.getId();

        List<Integer> wasMatchedByIds = usersMatches.stream()
                .distinct()
                .map(userMatches -> userMatches.getUser().getId())
                .filter(i -> !Objects.equals(i, userId))
                .collect(Collectors.toList());
        List<Integer> matchedIds = usersMatches.stream()
                .distinct()
                .map(userMatches -> userMatches.getUserMatched().getId())
                .filter(i -> !Objects.equals(i, userId))
                .collect(Collectors.toList());
        List<Integer> commonIds = new ArrayList<>(wasMatchedByIds);
        commonIds.retainAll(matchedIds);

        List<UsersDTO> usersDTOS = new ArrayList<>();
        userRepository.findByIdIn(commonIds).forEach(user -> {
            final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
            List<AvatarsDTO> userAvatars = photoUtils.getUserAvatars(user.getUsername(), user.getId());
            List<String> userInterests = userUtils.getUserInterests(user);
            usersDTOS.add(userMapper.mapUserToDTO(user, avatar, userAvatars, userInterests, null));
        });

        return usersDTOS;
    }
}
