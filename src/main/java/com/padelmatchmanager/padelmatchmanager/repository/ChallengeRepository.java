package com.padelmatchmanager.padelmatchmanager.repository;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    // Find challenges by level
    List<Challenge> findByLevel(String level);

    // Find challenges by club
    List<Challenge> findByClub(String club);

    // Find challenges by state
    List<Challenge> findByState(ChallengeState state);

    // Find active challenges (challenges in "ACTIVE" state)
    List<Challenge> findByStateEquals(ChallengeState state);

    // Find expired challenges (challenges in "EXPIRED" state)
    List<Challenge> findByStateEqualsAndEndTimeBefore(ChallengeState state, LocalDateTime currentTime);

}

