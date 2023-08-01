package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PasswordEncoder passwordEncoder, PlayerRepository playerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.playerRepository = playerRepository;
    }


    public void savePlayer(Player player) {
        String rawPassword = player.getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        player.setPassword(encryptedPassword);
        player.setEnabled(Boolean.TRUE);//Maybe for later developments

        playerRepository.save(player);
    }

    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username).orElse(null);
    }

    public List<Player> getPlayersByIds(List<Long> playerIds) {
        return playerRepository.findAllById(playerIds);
    }

}
