package com.padelmatchmanager.padelmatchmanager.repository;

import com.padelmatchmanager.padelmatchmanager.model.GameResult;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {

    List<GameResult> findByPlayers(Player player);
}
