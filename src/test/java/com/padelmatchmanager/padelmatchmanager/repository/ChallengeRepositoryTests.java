package com.padelmatchmanager.padelmatchmanager.repository;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeRepositoryTests {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    public void testFindByLevel() {
        Challenge challenge1 = new Challenge();
        challenge1.setLevel("2");
        challengeRepository.save(challenge1);

        Challenge challenge2 = new Challenge();
        challenge2.setLevel("3");
        challengeRepository.save(challenge2);

        List<Challenge> challenges = challengeRepository.findByLevel("2");

        assertEquals(1, challenges.size());
        assertEquals("2", challenges.get(0).getLevel());
    }

    @Test
    public void testFindByState() {
        Challenge challenge1 = new Challenge();
        challenge1.setState(ChallengeState.ACTIVE);
        challengeRepository.save(challenge1);

        Challenge challenge2 = new Challenge();
        challenge1.setState(ChallengeState.EXPIRED);
        challengeRepository.save(challenge2);

        List<Challenge> challenges = challengeRepository.findByState(ChallengeState.ACTIVE);

        assertEquals(1, challenges.size());
        assertEquals(ChallengeState.ACTIVE, challenges.get(0).getState());
    }

}
