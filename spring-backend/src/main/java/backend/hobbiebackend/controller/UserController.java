package backend.hobbiebackend.controller;

import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.BusinessRegisterDto;
import backend.hobbiebackend.dto.UpdateAppClientDto;
import backend.hobbiebackend.dto.UpdateBusinessDto;
import backend.hobbiebackend.entities.AppClient;
import backend.hobbiebackend.entities.BusinessOwner;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.enums.UserRoleEnum;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.jwt.JwtRequest;
import backend.hobbiebackend.jwt.JwtResponse;
import backend.hobbiebackend.security.HobbieUserDetailsService;
import backend.hobbiebackend.service.NotificationService;
import backend.hobbiebackend.service.UserService;
import backend.hobbiebackend.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final HobbieUserDetailsService hobbieUserDetailsService;

    @PostMapping("/signup")
    @Operation(summary = "Create new client-user")
    public ResponseEntity<?> signup( @Valid @ModelAttribute AppClientSignUpDto userDTO) {
        if (userService.userExists(userDTO.getUsername(), userDTO.getEmail())) {
            throw new RuntimeException("Username or email address already in use.");
        }
        UserEntity user = userService.register(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    @Operation(summary = "Create new business-user")
    public ResponseEntity<?> registerBusiness(@RequestBody BusinessRegisterDto business) {
        if (this.userService.businessExists(business.getBusinessName()) || this.userService.userExists(business.getUsername(), business.getEmail())) {
            throw new RuntimeException("Username or email address already in use.");
        }
        BusinessOwner businessOwner = this.userService.registerBusiness(business);
        return new ResponseEntity<BusinessOwner>(businessOwner, HttpStatus.CREATED);
    }

    @GetMapping("/client")
    @Operation(summary = "show client-user information", security = @SecurityRequirement(name = "bearerAuth"))
    public UserEntity showUserDetails(@RequestParam String username) {
        return this.userService.findUserByUsername(username);
    }

    @GetMapping("/business")
    @Operation(summary = "Show business-user information", security = @SecurityRequirement(name = "bearerAuth"))
    public BusinessOwner showBusinessDetails(@RequestParam String username) {
        return this.userService.findBusinessByUsername(username);
    }

    @PutMapping("/user")
    @Operation(summary = "Update client-user information (use existing user id)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateUser(@RequestBody UpdateAppClientDto user) {
        AppClient client = this.userService.findAppClientById(user.getId());
        client.setPassword(this.passwordEncoder.encode(user.getPassword()));
        client.setGender(user.getGender().name());
        client.setFullName(user.getFullName());
        this.userService.saveUpdatedUserClient(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
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
        return new ResponseEntity<UserEntity>(userById, HttpStatus.CREATED);
    }

    @PutMapping("/business")
    @Operation(summary = "Update business-user, (use existing user id)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateBusiness(@RequestBody UpdateBusinessDto business) {
        BusinessOwner businessOwner = this.userService.findBusinessOwnerById(business.getId());
        if (this.userService.businessExists(business.getBusinessName()) && (!businessOwner.getBusinessName().equals(business.getBusinessName()))) {
            throw new RuntimeException("Business name already in use.");
        }
        businessOwner.setBusinessName(business.getBusinessName());
        businessOwner.setPassword(this.passwordEncoder.encode(business.getPassword()));
        businessOwner.setAddress(business.getAddress());
        this.userService.saveUpdatedUser(businessOwner);

        return new ResponseEntity<>(businessOwner, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Delete user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Integer> deleteUser(@PathVariable Integer id) {
        boolean isRemoved = this.userService.deleteUser(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
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
                = hobbieUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login based on user role after authentication", security = @SecurityRequirement(name = "bearerAuth"))
    public String logInUser(@RequestParam String username) {
        UserEntity userByUsername = this.userService.findUserByUsername(username);
        if (userByUsername.getRole().equals(UserRoleEnum.USER.name())) {
            return "USER";
        } else if (userByUsername.getRole().equals(UserRoleEnum.BUSINESS_USER.name())) {
            return "BUSINESS_USER";
        }
        return null;
    }
}


