package com.padelmatchmanager.padelmatchmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showCustomLoginPage() {
        return "login"; // Return the name of your custom login page without the ".html" extension
    }

    @GetMapping("/logout")
    public String logout() {
        // Perform any additional cleanup or logging out if needed
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // You can add more logout-related functionality here if required
        return "redirect:/login?logout"; // Redirect to the login page after logout
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request) {
        // You can customize the behavior for handling login failure here
        // For example, you can log the failed login attempts or show a custom error message on the login page.
        return "redirect:/login?error"; // Redirect to the login page with an error parameter to show the error message
    }
}

