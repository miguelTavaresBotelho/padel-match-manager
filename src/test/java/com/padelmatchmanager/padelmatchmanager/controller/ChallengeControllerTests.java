package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import com.padelmatchmanager.padelmatchmanager.utils.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChallengeController.class)
public class ChallengeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeService challengeService;


    @Test
    public void testJoinChallenge_Success() throws Exception {
        Player currentPlayer = new Player();
        currentPlayer.setUsername("testPlayer");



        when(SecurityUtils.getCurrentPlayer()).thenReturn(currentPlayer);
        doNothing().when(challengeService).joinChallenge(anyLong(), any(Player.class));

        mockMvc.perform(post("/joinChallenge/{challengeId}", 123L))
                .andExpect(status().isOk())
                .andExpect(content().string("testPlayer"));

        verify(challengeService, times(1)).joinChallenge(eq(123L), eq(currentPlayer));
    }

    @Test
    public void testJoinChallenge_Failure() throws Exception {
        Player currentPlayer = new Player();
        currentPlayer.setUsername("testPlayer");

        when(SecurityUtils.getCurrentPlayer()).thenReturn(currentPlayer);

        mockMvc.perform(post("/joinChallenge/{challengeId}", 123L))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while trying to join the challenge. Please try again later."));

        verify(challengeService, times(1)).joinChallenge(eq(123L), eq(currentPlayer));
    }
}

