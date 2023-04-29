package backend.hobbiebackend.controller;

import backend.hobbiebackend.dto.UserAvatarDeleteDto;
import backend.hobbiebackend.dto.UserAvatarDto;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.repostiory.UserRepository;
import backend.hobbiebackend.utils.PhotoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final PhotoUtils photoUtils;
    private final UserRepository userRepository;

    private List<String> hardcodedUserAvatarsNames = new ArrayList<>();

    @GetMapping("/getAvatar")
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public List<UserAvatarDto> getUserAvatar(@RequestParam String username) {
        List<UserAvatarDto> userAvatars = new ArrayList<>();
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            userAvatars.add(new UserAvatarDto(photoUtils.getEncodedFile(user.getAvatar(), user.getUsername()), user.getAvatar()));
            hardcodedUserAvatarsNames.forEach(name ->
                    userAvatars.add(new UserAvatarDto(photoUtils.getEncodedFile(name, user.getUsername()), name)));
        }
        return userAvatars;
    }

    @PostMapping("/userAvatar")
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void saveUserAvatar(@RequestParam String username, @ModelAttribute UserAvatarDeleteDto userAvatar) {
        photoUtils.savePhoto(userAvatar.getAvatar(), username);
        hardcodedUserAvatarsNames.add(userAvatar.getAvatarName());
    }

    @DeleteMapping("/deleteUserAvatar")
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteUserAvatar(@RequestParam String userAvatarName) {
        hardcodedUserAvatarsNames = hardcodedUserAvatarsNames.stream()
                .filter(name -> !name.equalsIgnoreCase(userAvatarName))
                .collect(Collectors.toList());
    }

    @PostMapping("/changeUserMainAvatar")
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void changeUserMainAvatar(@RequestParam String username, @RequestParam String userAvatarName) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        hardcodedUserAvatarsNames = hardcodedUserAvatarsNames.stream()
                .filter(name -> !name.equalsIgnoreCase(userAvatarName))
                .collect(Collectors.toList());
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            List<String> tmp = hardcodedUserAvatarsNames;
            hardcodedUserAvatarsNames = new ArrayList<>();
            hardcodedUserAvatarsNames.add(user.getAvatar());
            hardcodedUserAvatarsNames.addAll(tmp);
            hardcodedUserAvatarsNames = hardcodedUserAvatarsNames.stream()
                    .filter(name -> !name.equalsIgnoreCase(userAvatarName))
                    .collect(Collectors.toList());
            user.setAvatar(userAvatarName);
            userRepository.saveAndFlush(user);
        }

    }
}
