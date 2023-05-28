package backend.hobbiebackend.controller;

import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.service.MatchesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchesController {

    private final MatchesService matchesService;

    @GetMapping("/possible-matches")
    @Operation(summary = "Show possible matches", security = @SecurityRequirement(name = "bearerAuth"))
    public List<UsersDTO> showPossibleMatches(@RequestParam String username) {
        return matchesService.getAllUsersMatchesForClient(username);
    }

    @GetMapping("/get-my-matches")
    @Operation(summary = "Get my matches", security = @SecurityRequirement(name = "bearerAuth"))
    public List<UsersDTO> getMyMatches(@RequestParam String username) {
        return matchesService.getAllMyMatches(username);
    }

    @PostMapping("/add-to-matches")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add to matches", security = @SecurityRequirement(name = "bearerAuth"))
    public void addToMatches(@RequestParam String username, @RequestParam Integer matchedUserId, @RequestParam String status) {
        matchesService.addUserToMatches(username, matchedUserId, status);
    }
}
