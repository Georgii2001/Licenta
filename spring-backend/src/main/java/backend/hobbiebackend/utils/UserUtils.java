package backend.hobbiebackend.utils;

import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.repostiory.UserInterestsRepository;
import backend.hobbiebackend.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;
    private final UserInterestsRepository userInterestsRepository;

    public UserEntity getUserEntity(String username, Integer id, String email) {
        Optional<UserEntity> userEntity = Optional.empty();
        if (email != null) {
            userEntity = userRepository.findByEmail(email);
        } else if (username != null) {
            userEntity = userRepository.findByUsername(username);
        } else if (id != null) {
            userEntity = userRepository.findById(id);
        }
        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            throw new NotFoundException("Can not find user with this email, id or username");
        }
    }

    public List<String> getUserInterests(UserEntity user) {
        return userInterestsRepository.findByUserEntityId(user.getId()).stream()
                .map(userInterest -> userInterest.getInterests().getInterestName())
                .collect(Collectors.toList());
    }
}
