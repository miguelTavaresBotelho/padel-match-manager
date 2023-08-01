package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import com.padelmatchmanager.padelmatchmanager.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.List;

@Controller
public class MainController {

    private ChallengeService challengeService;

    private final PlayerService playerService;


    @Autowired
    public MainController(ChallengeService challengeService, PlayerService playerService) {
        this.challengeService = challengeService;
        this.playerService = playerService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        Player currentPlayer = SecurityUtils.getCurrentPlayer();
        model.addAttribute("username", currentPlayer != null ? currentPlayer.getUsername() : "Guest");

        List<Challenge> activeChallenges = challengeService.getActiveChallenges();
        model.addAttribute("activeChallenges", activeChallenges);
        model.addAttribute("newChallenge", new Challenge());

        return "main-page";
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createChallenge")
    public String createChallenge(@ModelAttribute("newChallenge") @Valid Challenge newChallenge,
                                  @RequestParam("playerIds") List<Long> playerIds,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, redisplay the main page with error messages
            List<Challenge> activeChallenges = challengeService.getActiveChallenges();
            model.addAttribute("activeChallenges", activeChallenges);
            return "main-page";
        }

        try {
            List<Player> players = playerService.getPlayersByIds(playerIds);
            newChallenge.setPlayers(players);
            challengeService.createChallenge(newChallenge);
        } catch (Exception e) {
            // Handle the exception (e.g., log it) and display an error message to the player
            model.addAttribute("errorMessage", "Error creating the challenge. Please try again.");
            List<Challenge> activeChallenges = challengeService.getActiveChallenges();
            model.addAttribute("activeChallenges", activeChallenges);
            return "main-page";
        }

        return "redirect:/main";
    }

}