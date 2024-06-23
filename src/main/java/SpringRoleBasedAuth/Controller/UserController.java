package SpringRoleBasedAuth.Controller;

import SpringRoleBasedAuth.Dto.AuthResponse;
import SpringRoleBasedAuth.Dto.UserLoginDto;
import SpringRoleBasedAuth.Dto.UserRegistrationDto;
import SpringRoleBasedAuth.Entity.User;
import SpringRoleBasedAuth.Exception.ResourceNotFoundException;
import SpringRoleBasedAuth.JwtService.CustomUserDetailsService;
import SpringRoleBasedAuth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import SpringRoleBasedAuth.Enum.Role;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("User registered successfully with email id : "+userDto.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticateUser(@RequestBody UserLoginDto loginDto) {
        try {
            AuthResponse authResponse = customUserDetailsService.authenticateUser(loginDto);
            return ResponseEntity.ok(authResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
        }
    }
}
