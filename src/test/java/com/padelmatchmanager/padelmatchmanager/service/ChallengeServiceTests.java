package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.ChallengeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ChallengeServiceTests {

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private ChallengeService challengeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateChallenge() {
        Challenge challenge = new Challenge();
        challenge.setCreationDate(LocalDateTime.now());
        challenge.setState(ChallengeState.ACTIVE);

        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        Challenge createdChallenge = challengeService.createChallenge(challenge);

        verify(challengeRepository).save(challenge);

        assertEquals(challenge.getCreationDate(), createdChallenge.getCreationDate());
        assertEquals(ChallengeState.ACTIVE, createdChallenge.getState());
    }

    @Test
    public void testGetActiveChallenges() {
        Challenge challenge1 = new Challenge();
        Challenge challenge2 = new Challenge();

        List<Challenge> mockChallenges = List.of(challenge1, challenge2);

        when(challengeRepository.findByStateEquals(ChallengeState.ACTIVE)).thenReturn(mockChallenges);

        List<Challenge> activeChallenges = challengeService.getActiveChallenges();

        verify(challengeRepository).findByStateEquals(ChallengeState.ACTIVE);

        assertEquals(2, activeChallenges.size());
    }

    @Test
    public void testJoinChallenge() {
        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setPlayers(new ArrayList<>());

        Player currentPlayer = new Player();
        currentPlayer.setUsername("testPlayer");

        when(challengeRepository.findById(anyLong())).thenReturn(Optional.of(challenge));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        challengeService.joinChallenge(1L, currentPlayer);

        verify(challengeRepository).findById(1L);
        verify(challengeRepository).save(challenge);

        assertEquals(1, challenge.getPlayers().size());
        assertEquals(ChallengeState.CLOSED, challenge.getState());
    }

}

