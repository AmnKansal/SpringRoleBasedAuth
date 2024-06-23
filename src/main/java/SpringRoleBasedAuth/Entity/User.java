package SpringRoleBasedAuth.Entity;

import SpringRoleBasedAuth.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class User {
    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private int age;
    private String address;
    private String dateOfRegistration;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
