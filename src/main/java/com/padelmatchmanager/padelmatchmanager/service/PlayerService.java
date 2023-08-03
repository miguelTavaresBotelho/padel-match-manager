package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.Player;

import java.util.List;

public interface PlayerService {

    Player getPlayerByUsername(String username);

    List<Player> getPlayersByIds(List<Long> playerIds);

    void savePlayer(Player player);
}
