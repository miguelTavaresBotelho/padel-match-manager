package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void testAutocompletePlayers() throws Exception {
        List<Player> mockPlayers = new ArrayList<>();
        Player player1 = new Player();
        player1.setId(1L);
        player1.setEnabled(true);
        player1.setUsername("player1");

        Player player2 = new Player();
        player2.setId(2L);
        player2.setEnabled(true);
        player2.setUsername("player2");

        mockPlayers.add(player1);
        mockPlayers.add(player2);

        when(playerRepository.findByUsernameContainingIgnoreCase(anyString())).thenReturn(mockPlayers);

        mockMvc.perform(get("/autocomplete/players")
                        .param("partialName", "player"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("player1"))
                .andExpect(jsonPath("$[1].username").value("player2"));

        verify(playerRepository, times(1)).findByUsernameContainingIgnoreCase(eq("player"));
    }
}
