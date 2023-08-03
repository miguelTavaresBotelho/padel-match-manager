package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import com.padelmatchmanager.padelmatchmanager.service.ChallengeService;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeService challengeService;

    @MockBean
    private PlayerService playerService;

    @Test
    public void testShowMainPage() throws Exception {
        List<Challenge> activeChallenges = new ArrayList<>();

        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setLevel("2");
        challenge.setClub("Padel Club");
        challenge.setStartTime(LocalDateTime.now());
        challenge.setPlayTime(Duration.ofHours(2));
        challenge.setState(ChallengeState.ACTIVE);

        activeChallenges.add(challenge);
        when(challengeService.getActiveChallenges()).thenReturn(activeChallenges);

        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main-page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists(
                        "username", "activeChallenges", "newChallenge"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateChallenge() throws Exception {
        Challenge newChallenge = new Challenge();
        newChallenge.setId(1L);
        newChallenge.setLevel("Intermediate");
        newChallenge.setClub("Padel Club");
        newChallenge.setStartTime(LocalDateTime.now());
        newChallenge.setPlayTime(Duration.ofHours(2));
        newChallenge.setState(ChallengeState.ACTIVE);

        List<Long> playerIds = new ArrayList<>();
        when(playerService.getPlayersByIds(playerIds)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/createChallenge")
                        .param("playerIds", "1,2,3")
                        .param("playTime", "PT1H")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("newChallenge", newChallenge))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/main"));
    }
}
