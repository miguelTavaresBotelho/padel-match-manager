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
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/login?logout";
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request) {
        return "redirect:/login?error";
    }
}

