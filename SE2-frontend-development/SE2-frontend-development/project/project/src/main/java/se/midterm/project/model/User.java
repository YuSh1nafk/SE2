package se.midterm.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class User {

    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }


    private String gender;

    private LocalDate dateOfBirth;

    private String position;

    public User(String gender, LocalDate dateOfBirth, String position, String profileImageUrl, Long id, String username, String password, String email, String role) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.profileImageUrl = profileImageUrl;
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    @Lob
    private byte[] profileImage;

    private String profileImageContentType;


    private String profileImageUrl;

    public @NotBlank(message = "Username cannot be blank") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Password cannot be blank") String getPassword() {
        return password;
    }

    public @Email(message = "Email is not in correct format") @NotBlank(message = "Email cannot be blank") String getEmail() {
        return email;
    }

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