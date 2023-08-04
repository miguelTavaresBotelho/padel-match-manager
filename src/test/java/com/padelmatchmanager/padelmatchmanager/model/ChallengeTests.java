package com.padelmatchmanager.padelmatchmanager.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeTests {

    @Test
    public void testChallengeFields() {
        Challenge challenge = new Challenge();
        challenge.setLevel("2+");
        challenge.setClub("Example Club");
        LocalDateTime startTime = LocalDateTime.now();
        challenge.setStartTime(startTime);
        Duration playTime = Duration.ofMinutes(60);
        challenge.setPlayTime(playTime);
        ChallengeState state = ChallengeState.ACTIVE;
        challenge.setState(state);
        LocalDateTime creationDate = LocalDateTime.now();
        challenge.setCreationDate(creationDate);

        assertEquals("2+", challenge.getLevel());
        assertEquals("Example Club", challenge.getClub());
        assertEquals(startTime, challenge.getStartTime());
        assertEquals(playTime, challenge.getPlayTime());
        assertEquals(state, challenge.getState());
        assertEquals(creationDate, challenge.getCreationDate());
    }

    @Test
    public void testChallengePlayers() {
        Challenge challenge = new Challenge();
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        players.add(player1);
        players.add(player2);
        challenge.setPlayers(players);

        assertEquals(2, challenge.getPlayers().size());
        assertEquals(player1, challenge.getPlayers().get(0));
        assertEquals(player2, challenge.getPlayers().get(1));
    }

    @Test
    public void testChallengeDefaultConstructor() {
        Challenge challenge = new Challenge();

        assertNotNull(challenge.getPlayers());
        assertNull(challenge.getId());
        assertNull(challenge.getLevel());
        assertNull(challenge.getClub());
        assertNull(challenge.getStartTime());
        assertNull(challenge.getPlayTime());
        assertNull(challenge.getState());
        assertNull(challenge.getCreationDate());
    }

}

