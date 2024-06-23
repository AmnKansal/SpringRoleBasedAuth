package SpringRoleBasedAuth.Service;


import SpringRoleBasedAuth.Dto.UserRegistrationDto;
import SpringRoleBasedAuth.Entity.User;
import SpringRoleBasedAuth.Exception.ResourceNotFoundException;
import SpringRoleBasedAuth.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDto userDto) {
        if (userRepository.existsById(userDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setAge(userDto.getAge());
        user.setAddress(userDto.getAddress());
        user.setRoles(userDto.getRoles());
        user.setDateOfRegistration(LocalDateTime.now().toString());
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


}
