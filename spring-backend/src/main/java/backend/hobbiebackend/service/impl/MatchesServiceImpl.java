package backend.hobbiebackend.service.impl;

import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.mapper.UserMapper;
import backend.hobbiebackend.models.UserMatches;
import backend.hobbiebackend.repostiory.UserInterestsRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.service.MatchesService;
import backend.hobbiebackend.utils.PhotoUtils;
import backend.hobbiebackend.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchesServiceImpl implements MatchesService {

    final private UserUtils userUtils;
    final private PhotoUtils photoUtils;
    final private UserMapper userMapper;
    final private UserRepository userRepository;
    final private UserInterestsRepository userInterestsRepository;

    @Override
    public List<UsersDTO> getAllUsersMatchesForClient(String username) {

        UserEntity userEntity = userUtils.getUserEntity(username, null, null);

        List<UserMatches> userMatches = userInterestsRepository.countCommonInterestsByUserId(userEntity.getId());

        Map<Integer, Long> userMatchesMap = userMatches.stream()
                .collect(Collectors.toMap(UserMatches::getUserId, UserMatches::getMatches));

        return userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equalsIgnoreCase(username))
                .map(user -> {
                    final String avatar = photoUtils.getEncodedFile(user.getAvatar(), user.getUsername());
                    return userMapper.mapUserToDTO(user, avatar, null, null, userMatchesMap.getOrDefault(user.getId(), 0L));
                })
                .sorted(Comparator.comparing(UsersDTO::getUserMatchCount))
                .collect(Collectors.toList());
    }
}
