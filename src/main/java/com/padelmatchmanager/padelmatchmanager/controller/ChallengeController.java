package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.security.CustomUserDetails;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChallengeController {
    private final ChallengeService challengeService;
    private final PlayerService playerService;

    public ChallengeController(ChallengeService challengeService, PlayerService playerService) {
        this.challengeService = challengeService;
        this.playerService = playerService;
    }

    private Player getCurrentPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Player currentPlayer = playerService.getPlayerByUsername(username);
        return currentPlayer;
    }

    @PostMapping("/joinChallenge/{challengeId}")
    public ResponseEntity<String> joinChallenge(@PathVariable Long challengeId) {
        try {
            Player currentPlayer = getCurrentPlayer();

            challengeService.joinChallenge(challengeId, currentPlayer);
            return ResponseEntity.ok("You have successfully joined the challenge!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to join the challenge. Please try again later.");
        }
    }

}

