package SpringRoleBasedAuth.Dto;

import SpringRoleBasedAuth.Enum.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserRegistrationDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private int age;
    private String address;
    private String dateOfRegistration;
    private Set<Role> roles;
}
