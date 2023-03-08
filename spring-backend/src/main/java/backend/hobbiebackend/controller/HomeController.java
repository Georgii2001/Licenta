package backend.hobbiebackend.controller;

import backend.hobbiebackend.model.entities.UserEntity;
import backend.hobbiebackend.service.HobbyService;
import backend.hobbiebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HobbyService hobbyService;
    private final UserService userService;

    @GetMapping("/home")
    @Operation(summary = "Show client/business homepage", security = @SecurityRequirement(name = "bearerAuth"))
    public Set<UserEntity> hobbiesShow(@RequestParam String username, @RequestParam String role) {
        // if (role.equals("user")) {
        return userService.getAllUsersMatchesForClient(username);
        //  return hobbyService.getAllHobbieMatchesForClient(username);
        //  }
        // return hobbyService.getAllHobbiesForBusiness(username);
    }
}
