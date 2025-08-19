package travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import travel.dto.AppClientSignUpDto;
import travel.dto.EmailResponseDTO;
import travel.dto.UpdateAppClientDto;
import travel.dto.UsersDTO;
import travel.entities.UserEntity;
import travel.enums.UserRoleEnum;
import travel.jwt.JwtRequest;
import travel.jwt.JwtResponse;
import travel.security.UserDetailsServiceImpl;
import travel.service.UserService;
import travel.utils.JWTUtils;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    @Operation(summary = "Create new client-user")
    public ResponseEntity<?> signup(@Valid @ModelAttribute AppClientSignUpDto userDTO) {
        if (userService.userExists(userDTO.getUsername(), userDTO.getEmail())) {
            throw new RuntimeException("Username or email address already in use.");
        }
        UserEntity user = userService.register(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/client")
    @Operation(summary = "Show client-user information", security = @SecurityRequirement(name = "bearerAuth"))
    public UsersDTO showUserDetails(@RequestParam(required = false) String username, @RequestParam(required = false) Integer id) {
        return this.userService.getUserMainDetails(username, id);
    }

    @PutMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update client-user information (use existing user id)", security = @SecurityRequirement(name = "bearerAuth"))
    public void updateUser(@RequestBody UpdateAppClientDto user) {
        userService.updatedUserEntity(user);
    }

    @PostMapping("/send-invitation")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send invitation to the new trip", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EmailResponseDTO> sendInvitation(@RequestParam String username, @RequestParam Integer receiverId) {
        return ResponseEntity.ok(userService.sendEmailInvitation(username, receiverId));
    }

    @PutMapping("/password")
    @Operation(summary = "Update password, (use existing user id)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> setUpNewPassword(@RequestParam Integer id, String password) {
        UserEntity userById = this.userService.findUserById(id);
        userById.setPassword(this.passwordEncoder.encode(password));
        this.userService.saveUserWithUpdatedPassword(userById);
        return new ResponseEntity<>(userById, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user and get JWT Token")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails
                = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login based on user role after authentication", security = @SecurityRequirement(name = "bearerAuth"))
    public String logInUser(@RequestParam String username) {
        UsersDTO userByUsername = this.userService.getUserMainDetails(username, null);
        if (userByUsername.getRole().equals(UserRoleEnum.USER.name())) {
            return "USER";
        } else if (userByUsername.getRole().equals(UserRoleEnum.BUSINESS_USER.name())) {
            return "BUSINESS_USER";
        }
        return null;
    }

    @PostMapping("/notification")
    @Operation(summary = "Send notification with password reset link", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> sendNotification(@RequestParam("email") String email) {
        UserEntity user = this.userService.sendPasswordEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}


