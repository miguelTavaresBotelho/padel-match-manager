package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.controller.ChallengeController;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ChallengeController.class)
public class PlayerServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl(passwordEncoder, playerRepository);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePlayer() {
        Player player = new Player();
        player.setUsername("testUser");
        player.setPassword("testPassword");

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        playerService.savePlayer(player);

        verify(passwordEncoder).encode("testPassword");
        verify(playerRepository).save(player);

        assertEquals("encodedPassword", player.getPassword());
    }

    @Test
    public void testGetPlayerByUsername() {
        Player player = new Player();
        player.setUsername("testUser");

        when(playerRepository.findByUsername(anyString())).thenReturn(Optional.of(player));

        Player foundPlayer = playerService.getPlayerByUsername("testUser");

        verify(playerRepository).findByUsername("testUser");

        assertEquals("testUser", foundPlayer.getUsername());
    }

    @Test
    public void testGetPlayersByIds() {
        List<Long> playerIds = new ArrayList<>();
        playerIds.add(1L);
        playerIds.add(2L);

        Player player1 = new Player();
        player1.setId(1L);
        player1.setUsername("user1");

        Player player2 = new Player();
        player2.setId(2L);
        player2.setUsername("user2");

        when(playerRepository.findAllById(playerIds)).thenReturn(List.of(player1, player2));

        List<Player> players = playerService.getPlayersByIds(playerIds);

        verify(playerRepository).findAllById(playerIds);

        assertEquals(2, players.size());
        assertEquals("user1", players.get(0).getUsername());
        assertEquals("user2", players.get(1).getUsername());
    }
}


