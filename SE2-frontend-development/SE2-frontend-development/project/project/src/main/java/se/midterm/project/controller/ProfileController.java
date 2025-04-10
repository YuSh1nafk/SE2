package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.midterm.project.model.MyUserDetail;
import se.midterm.project.model.User;
import se.midterm.project.repository.UserRepository;

@Controller
public class ProfileController {

    @GetMapping("/admin/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminProfile(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);
        return "admin/adminProfile";
    }

    @GetMapping("/customer/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);
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

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute User updatedUser, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User currentUser = userDetail.getUser();
        currentUser.setFullName(updatedUser.getFullName());
        currentUser.setGender(updatedUser.getGender());
        currentUser.setDateOfBirth(updatedUser.getDateOfBirth());
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            currentUser.setPosition(updatedUser.getPosition());
        }
        userRepository.save(currentUser);
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/profile";
        } else {
            return "redirect:/customer/profile";
        }
    }


}