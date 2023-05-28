package travel.controller;

import travel.dto.UserAvatarDto;
import travel.service.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping("/avatar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Save user avatar", security = @SecurityRequirement(name = "bearerAuth"))
    public void saveAvatar(@RequestParam String username, @ModelAttribute UserAvatarDto userAvatar) {
        avatarService.saveUserAvatar(username, userAvatar);
    }

    @DeleteMapping("/avatar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete user avatar", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteAvatar(@RequestParam String username, @RequestParam Integer avatarId) {
        avatarService.deleteUserAvatar(username, avatarId);
    }

    @PostMapping("/changeMainAvatar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change user main avatar", security = @SecurityRequirement(name = "bearerAuth"))
    public void changeMainAvatar(@RequestParam String username, @RequestParam Integer avatarId) {
        avatarService.changeUserMainAvatar(username, avatarId);
    }
}
