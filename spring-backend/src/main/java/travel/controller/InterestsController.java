package travel.controller;

import travel.service.InterestsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterestsController {

    private final InterestsService interestsService;

    @GetMapping("/interests")
    @Operation(summary = "Get unassigned interests", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<String>> getInterests(@RequestParam String username) {
        return ResponseEntity.ok(interestsService.getUnassignedInterests(username));
    }

    @PostMapping("/userInterests")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void saveUserInterests(@RequestParam String username, @RequestParam List<String> interestsList) {
        interestsService.saveNewUserInterest(username, interestsList);
    }

    @PostMapping("/discoverInterests")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Save user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void discoverUserInterests(@RequestParam Integer userId, @RequestParam List<String> interestsList) {
        interestsService.discoverNewInterests(userId, interestsList);
    }

    @DeleteMapping("/userInterests")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete user interests", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteUserInterests(@RequestParam String username, @RequestParam String interest) {
        interestsService.removeCurrentUserInterest(username, interest);
    }
}
