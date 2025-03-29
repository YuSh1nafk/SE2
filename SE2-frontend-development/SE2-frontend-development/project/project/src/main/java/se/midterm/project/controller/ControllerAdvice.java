package se.midterm.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ModelAttribute
    public void addAttributes(Model model, Principal principal, Authentication auth) {
        boolean isLoggedIn = principal != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            model.addAttribute("username", principal.getName());
            boolean isAdmin = auth != null && auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
    }

}

