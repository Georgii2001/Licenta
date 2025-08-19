package travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travel.dto.EmailResponseDTO;
import travel.dto.UsersDTO;
import travel.service.MatchesService;

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
    @Operation(summary = "Add to matches", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EmailResponseDTO> addToMatches(@RequestParam String username, @RequestParam Integer matchedUserId, @RequestParam String status) {
        return ResponseEntity.ok(matchesService.addUserToMatches(username, matchedUserId, status));
    }
}
