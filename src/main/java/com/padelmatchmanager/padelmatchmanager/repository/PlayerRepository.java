package com.padelmatchmanager.padelmatchmanager.repository;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUsername(String username);

    List<Player> findByUsernameContainingIgnoreCase(String partialName);
}
