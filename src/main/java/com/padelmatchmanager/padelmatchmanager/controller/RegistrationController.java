package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private PlayerService playerService;

    public RegistrationController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPlayer(@RequestParam String username, @RequestParam String password) {
        Player newPlayer = new Player();
        newPlayer.setUsername(username);
        newPlayer.setPassword(password);

        playerService.savePlayer(newPlayer);

        return "redirect:/login";
    }
}

