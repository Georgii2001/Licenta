package backend.hobbiebackend.controller;

import backend.hobbiebackend.dto.*;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.enums.UserRoleEnum;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.jwt.JwtRequest;
import backend.hobbiebackend.jwt.JwtResponse;
import backend.hobbiebackend.security.UserDetailsServiceImpl;
import backend.hobbiebackend.service.NotificationService;
import backend.hobbiebackend.service.UserService;
import backend.hobbiebackend.utils.JWTUtils;
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

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
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

    @PostMapping("/notification")
    @Operation(summary = "Send notification with password reset link", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> sendNotification(@RequestParam("email") String e) {
        UserEntity userByEmail = this.userService.findUserByEmail(e);
        if (userByEmail == null) {
            throw new NotFoundException("User not found");
        } else {
            this.notificationService.sendNotification(userByEmail);
        }
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
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
}


