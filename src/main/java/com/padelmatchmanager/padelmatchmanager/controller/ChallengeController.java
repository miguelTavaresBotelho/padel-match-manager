package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import com.padelmatchmanager.padelmatchmanager.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/joinChallenge/{challengeId}")
    public ResponseEntity<String> joinChallenge(@PathVariable Long challengeId) {
        try {
            Player currentPlayer = SecurityUtils.getCurrentPlayer();

            challengeService.joinChallenge(challengeId, currentPlayer);
            return ResponseEntity.ok(currentPlayer.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to join the challenge. Please try again later.");
        }
    }

}

