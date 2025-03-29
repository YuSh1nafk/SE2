package se.midterm.project.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ModelAttribute
    public void addAttributes(Model model, Principal principal) {
        model.addAttribute("isLoggedIn", principal != null); // Nếu người dùng đã đăng nhập, gán là true
    }
}

