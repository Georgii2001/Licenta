package travel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel.dto.AvatarsDTO;
import travel.dto.EmailResponseDTO;
import travel.dto.UsersDTO;
import travel.email.models.EmailRequest;
import travel.email.utils.EmailUtils;
import travel.entities.MatchesStatus;
import travel.entities.UserEntity;
import travel.entities.UsersMatches;
import travel.handler.AlreadyExistsException;
import travel.handler.NotFoundException;
import travel.mapper.UserMapper;
import travel.repostiory.MatchesStatusRepository;
import travel.repostiory.UserInterestsRepository;
import travel.repostiory.UserMatchesRepository;
import travel.repostiory.UserRepository;
import travel.service.MatchesService;
import travel.utils.PhotoUtils;
import travel.utils.UserUtils;

import java.util.*;
import java.util.stream.Collectors;

import static travel.constants.Constants.MATCHED_STATUS;
import static travel.email.constants.EmailConstants.MESSAGE_WAS_ALREADY_SENT;
import static travel.email.constants.TemplateNames.USER_HAS_NEW_MATCHES;

@Slf4j
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
    private final EmailUtils emailUtils;


    public List<UsersDTO> getAllUsersMatchesForClient(String username) {
        log.info("getAllUsersMatchesForClient: Getting all users matches for client with username: {}", username);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.error("Can not find user with username: {}", username);
            throw new NotFoundException("Can not find user with this username");
        }


        List<Integer> myUserInterests = userInterestsRepository.findByUserEntityId(user.get().getId())
                .stream().map(interest -> interest.getInterests().getInterestCode()).collect(Collectors.toList());

        List<Integer> allUserIds = userRepository.findAll()
                .stream()
                .filter(u -> u != user.get())
                .map(UserEntity::getId)
                .collect(Collectors.toList());

        Map<Integer, Double> covarianceValues = new HashMap<>();
        for (int userId : allUserIds) {
            List<Integer> userInterests = userInterestsRepository.findByUserEntityId(userId)
                    .stream().map(interest -> interest.getInterests().getInterestCode())
                    .collect(Collectors.toList());
            double covariance = calculateCovariance(userInterests, myUserInterests);
            covarianceValues.put(userId, covariance);

        }

        List<Integer> sortedDescendantUserIds = new ArrayList<>(covarianceValues.keySet());
        sortedDescendantUserIds.sort((u1, u2) -> {
            double covariance1 = covarianceValues.get(u1);
            double covariance2 = covarianceValues.get(u2);
            return Double.compare(covariance2, covariance1);
        });


        List<UsersMatches> usersMatches = userMatchesRepository.findByUserEquals(user.get());
        List<Integer> matchedIds = usersMatches.stream()
                .distinct()
                .map(userMatch -> userMatch.getUserMatched().getId())
                .filter(i -> !Objects.equals(i, user.get().getId()))
                .collect(Collectors.toList());

        log.debug("Found {} matched user ids for user: {}", matchedIds.size(), username);
        return sortedDescendantUserIds.stream()
                .filter(userId -> !matchedIds.contains(userId))
                .filter(userId -> !Objects.equals(userId, user.get().getId()))
                .map(u -> {
                    UserEntity userEntity = userRepository.findById(u).get();
                    final String avatar = photoUtils.getEncodedFile(userEntity.getAvatar(), userEntity.getUsername());
                    List<AvatarsDTO> userAvatars = photoUtils.getUserAvatars(userEntity.getUsername(), userEntity.getId());
                    List<String> userInterests = userUtils.getUserInterests(userEntity);
                    return userMapper.mapUserToDTO(userEntity, avatar, userAvatars, userInterests, null);
                })
                .collect(Collectors.toList());

    }

    private static double calculateCovariance(List<Integer> userInterests, List<Integer> myUserInterests) {
        final double mean1 = userInterests.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);


        final double mean2 = myUserInterests.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double covariance = 0.0;
        final int n = Math.min(userInterests.size(), myUserInterests.size());

        for (int i = 0; i < n; i++) {
            double deviation1 = userInterests.get(i) - mean1;
            double deviation2 = myUserInterests.get(i) - mean2;

            covariance += deviation1 * deviation2;
        }
        covariance /= n;

        return covariance;
    }

    @Override
    public EmailResponseDTO addUserToMatches(String username, Integer matchedUserId, String status) {
        log.info("addUserToMatches: Adding user with username: {} to matches with userId: {} and status: {}", username, matchedUserId, status);
        MatchesStatus matchesStatus = obtainStatusFromDB(status);

        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        List<UsersMatches> usersMatchesList = userMatchesRepository
                .findByUserIdEqualsAndUserMatchedIdEquals(userEntity.getId(), matchedUserId);

        if (usersMatchesList.isEmpty()) {
            log.debug("addUserToMatches: User with username: {} is not already matched with userId: {}", username, matchedUserId);
            UsersMatches usersMatches = new UsersMatches();
            UserEntity userMatchedEntity = userUtils.getUserEntity(null, matchedUserId, null);

            usersMatches.setUser(userEntity);
            usersMatches.setUserMatched(userMatchedEntity);
            usersMatches.setMatchesStatus(matchesStatus);

            userMatchesRepository.saveAndFlush(usersMatches);

            List<UsersMatches> matchedUserList = userMatchesRepository
                    .findByUserIdEqualsAndUserMatchedIdEquals(matchedUserId, userEntity.getId());

            if (!matchedUserList.isEmpty() &&
                    matchedUserList.get(0).getMatchesStatus().getMatchesStatusName().equalsIgnoreCase(MATCHED_STATUS)) {

                EmailRequest emailRequest = new EmailRequest();

                emailRequest.setSender(userEntity);
                emailRequest.setReceiver(matchedUserList.get(0).getUser());

                return new EmailResponseDTO(emailUtils.notifyUser(emailRequest, USER_HAS_NEW_MATCHES.name()));
            }
        } else {
            log.warn("User with username: {} is already matched with userId: {}", username, matchedUserId);
            throw new AlreadyExistsException("You can find this user on my matches page!!!");
        }
        return new EmailResponseDTO(MESSAGE_WAS_ALREADY_SENT);
    }

    private MatchesStatus obtainStatusFromDB(String status) {
        log.info("obtainStatusFromDB: Obtaining matches status from DB with status name: {}", status);
        MatchesStatus matchesStatus = matchesStatusRepository.findByMatchesStatusNameEquals(status);

        if (matchesStatus == null) {
            log.error("Matches status with name: {} does not exist in DB", status);
            throw new NotFoundException("Sent matches status does not exists!!!");
        }
        return matchesStatus;
    }

    @Override
    public List<UsersDTO> getAllMyMatches(String email) {
        log.info("getAllMyMatches: Getting all my matches for email: {}", email);
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

        log.debug("Found {} matches for user with email: {}", usersDTOS.size(), email);
        return usersDTOS;
    }
}
