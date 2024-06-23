package SpringRoleBasedAuth.JwtService;

import SpringRoleBasedAuth.Configuration.JwtConfig;
import SpringRoleBasedAuth.Dto.AuthResponse;
import SpringRoleBasedAuth.Dto.UserLoginDto;
import SpringRoleBasedAuth.Entity.User;
import SpringRoleBasedAuth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import SpringRoleBasedAuth.Enum.Role;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(user);
    }

    public AuthResponse authenticateUser(UserLoginDto loginDto) {
        UserDetails userDetails = loadUserByUsername(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtConfig.generateToken(userDetails);
        Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());
        User user = new User();
        user.setEmail(userDetails.getUsername());
        user.setFirstName(userOptional.get().getFirstName());
        user.setLastName(userOptional.get().getLastName());
        user.setAge(userOptional.get().getAge());
        user.setGender(userOptional.get().getGender());
        user.setRoles(userOptional.get().getRoles());
        user.setAddress(userOptional.get().getAddress());
        user.setAddress(userOptional.get().getAddress());
        user.setPhone(userOptional.get().getPhone());
        user.setDateOfRegistration(userOptional.get().getDateOfRegistration());
        user.setPassword("you cannot see");
        return new AuthResponse(user, token);
    }

}
