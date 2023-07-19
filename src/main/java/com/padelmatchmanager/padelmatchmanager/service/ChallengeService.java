package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import com.padelmatchmanager.padelmatchmanager.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    // You'll need to inject the ChallengeRepository to interact with the database
    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Challenge createChallenge(Challenge challenge) {
        // Set the initial state of the challenge to "ACTIVE" when creating
        challenge.setCreationDate(LocalDateTime.now());
        challenge.setState(ChallengeState.ACTIVE);
        return challengeRepository.save(challenge);
    }

    public List<Challenge> getActiveChallenges() {
        return challengeRepository.findByStateEquals(ChallengeState.ACTIVE);
    }

    public List<Challenge> getExpiredChallenges() {
        LocalDateTime currentTime = LocalDateTime.now();
        return challengeRepository.findByStateEqualsAndEndTimeBefore(ChallengeState.ACTIVE, currentTime);
    }

    public List<Challenge> getChallengesByLevel(String level) {
        return challengeRepository.findByLevel(level);
    }

    public List<Challenge> getChallengesByClub(String club) {
        return challengeRepository.findByClub(club);
    }

    public Optional<Challenge> getChallengeById(Long id) {
        return challengeRepository.findById(id);
    }
}

