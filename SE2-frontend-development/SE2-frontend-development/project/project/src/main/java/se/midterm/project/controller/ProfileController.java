package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.midterm.project.model.MyUserDetail;
import se.midterm.project.model.User;
import se.midterm.project.repository.UserRepository;
import se.midterm.project.service.JpaUserDetailsService;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class ProfileController {

    @GetMapping("/admin/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminProfile(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);
        model.addAttribute("hasProfileImage", user.getProfileImage() != null && user.getProfileImage().length > 0);
        return "admin/adminProfile";
    }

    @GetMapping("/customer/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);
        model.addAttribute("hasProfileImage", user.getProfileImage() != null && user.getProfileImage().length > 0);
        return "customer/userProfile";
    }


    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin/editAdminProfile";
        } else {
            return "customer/editUserProfile";
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JpaUserDetailsService userService;

    @PostMapping("/profile/save")
    public String saveProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam("profileImage") MultipartFile profileImage,
            Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User currentUser = userDetail.getUser();

        currentUser.setFullName(fullName);
        currentUser.setGender(gender);
        currentUser.setDateOfBirth(LocalDate.parse(dateOfBirth));
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) && position != null) {
            currentUser.setPosition(position);
        }

        if (!profileImage.isEmpty()) {
            try {
                currentUser.setProfileImage(profileImage.getBytes());
                currentUser.setProfileImageContentType(profileImage.getContentType());
            } catch (IOException e) {
                System.err.println("Failed to upload image: " + e.getMessage());
            }
        }

        userRepository.save(currentUser);
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/profile";
        } else {
            return "redirect:/customer/profile";
        }
    }

    @GetMapping("/profile/image/{userId}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user != null && user.getProfileImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(user.getProfileImageContentType()))
                    .body(user.getProfileImage());
        }
        return ResponseEntity.notFound().build();
    }

}