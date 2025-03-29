package se.midterm.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    @Email(message = "Email is not in correct format")
    @NotBlank(message = "Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role = "USER";

    public User(UserDto userDto, PasswordEncoder passwordEncoder) {
        this.username = userDto.getUsername();
        this.password = passwordEncoder.encode(userDto.getPassword());
        this.email = userDto.getEmail();
        this.role = "USER";
    }
}