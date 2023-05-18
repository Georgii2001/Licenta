package backend.hobbiebackend.controller;

import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.service.MatchesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
