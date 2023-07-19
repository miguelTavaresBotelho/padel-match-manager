package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    public MainController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        List<Challenge> activeChallenges = challengeService.getActiveChallenges();
        model.addAttribute("activeChallenges", activeChallenges);
        model.addAttribute("newChallenge", new Challenge());
        return "main-page";
    }

    @PostMapping("/createChallenge")
    public String createChallenge(@ModelAttribute("newChallenge") @Valid Challenge newChallenge,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, redisplay the main page with error messages
            List<Challenge> activeChallenges = challengeService.getActiveChallenges();
            model.addAttribute("activeChallenges", activeChallenges);
            return "main-page";
        }

        try {
            challengeService.createChallenge(newChallenge);
        } catch (Exception e) {
            // Handle the exception (e.g., log it) and display an error message to the user
            model.addAttribute("errorMessage", "Error creating the challenge. Please try again.");
            List<Challenge> activeChallenges = challengeService.getActiveChallenges();
            model.addAttribute("activeChallenges", activeChallenges);
            return "main-page";
        }

        return "redirect:/main";
    }

    @GetMapping("/challengeDetails")
    public String showChallengeDetails(@RequestParam("id") Long challengeId, Model model) {
        Optional<Challenge> challenge = challengeService.getChallengeById(challengeId);

        if (challenge.isPresent()) {
            // If the challenge is found, add it to the model and display the details page
            model.addAttribute("challenge", challenge.get());
            return "challenge-details"; // Replace "challenge-details" with the name of your challenge details view template
        } else {
            // If the challenge is not found, display an error message to the user
            model.addAttribute("errorMessage", "Challenge not found.");
            List<Challenge> activeChallenges = challengeService.getActiveChallenges();
            model.addAttribute("activeChallenges", activeChallenges);
            return "main-page";
        }
    }
}