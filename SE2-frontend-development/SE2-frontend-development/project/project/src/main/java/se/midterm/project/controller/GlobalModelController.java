package se.midterm.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import se.midterm.project.model.MyUserDetail;

@ControllerAdvice
public class GlobalModelController {

    @ModelAttribute
    public void addCommonAttributes(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof MyUserDetail) {

            MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", userDetail.hasRole("ADMIN"));
            model.addAttribute("username", userDetail.getFullName());


        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }
}
