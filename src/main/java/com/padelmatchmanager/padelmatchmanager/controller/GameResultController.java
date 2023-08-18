package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.GameResult;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.GameResultService;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import com.padelmatchmanager.padelmatchmanager.utils.SecurityUtils;
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
public class GameResultController {

    private final GameResultService gameResultService;

    private final PlayerService playerService;

    public GameResultController(GameResultService gameResultService, PlayerService playerService) {
        this.gameResultService = gameResultService;
        this.playerService = playerService;
    }


    @GetMapping("/results")
    public String showMyResultsPage(Model model) {
        Player currentPlayer = SecurityUtils.getCurrentPlayer();
        model.addAttribute("currentPlayer", currentPlayer);

        List<GameResult> gameResults = gameResultService.getGameResults(currentPlayer);
        model.addAttribute("gameResults", gameResults);

        return "my-results";

    }

    @PostMapping("/createGameResult")
    public String createGameResult(@ModelAttribute("newGameResult") @Valid GameResult newGameResult,
                                   @RequestParam("playerIds") List<Long> playerIds,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Player currentPlayer = SecurityUtils.getCurrentPlayer();
            List<GameResult> gameResults = gameResultService.getGameResults(currentPlayer);
            model.addAttribute("gameResults", gameResults);
            return "my-results";
        }

        try {
            List<Player> players = playerService.getPlayersByIds(playerIds);
            newGameResult.setPlayers(players);

            gameResultService.createGameResult(newGameResult);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error creating the game result. Please try again.");
            Player currentPlayer = SecurityUtils.getCurrentPlayer();
            List<GameResult> gameResults = gameResultService.getGameResults(currentPlayer);
            model.addAttribute("gameResults", gameResults);
            return "my-results";
        }

        return "redirect:/results";
    }

}
