package se.midterm.project.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.midterm.project.model.User;
import se.midterm.project.model.UserDto;
import se.midterm.project.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signUp";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "signUp";
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "signUp";
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "signUp";
        }

        User user = new User(userDto, passwordEncoder);
        user.setRole("USER"); // Ensure new users are always USER role
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("registerSuccess", "User registered successfully");

        return "redirect:/auth/login";
    }


    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "registerSuccess", required = false) String registerSuccess,
                                Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password");
        }
        if (registerSuccess != null) {
            model.addAttribute("registerSuccess", "Registration successful! Please login");
        }
        return "signIn";
    }
}