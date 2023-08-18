package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.GameResult;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.GameResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameResultService {


    private final GameResultRepository gameResultRepository;

    @Autowired
    public GameResultService(GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
    }

    public List<GameResult> getGameResults(Player player) {
        return gameResultRepository.findByPlayers(player);
    }

    public void createGameResult(GameResult newGameResult) {
        gameResultRepository.save(newGameResult);
    }
}
