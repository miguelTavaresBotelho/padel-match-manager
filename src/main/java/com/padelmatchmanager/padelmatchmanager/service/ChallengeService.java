package com.padelmatchmanager.padelmatchmanager.service;

import com.padelmatchmanager.padelmatchmanager.model.Challenge;
import com.padelmatchmanager.padelmatchmanager.model.ChallengeState;
import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.repository.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Challenge createChallenge(Challenge challenge) {
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

    public void joinChallenge(Long challengeId, Player currentPlayer) {
        //TODO check if the player is already in the challenge challenge.getPlayers().contains(currentPlayer)?
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(
                () -> new EntityNotFoundException("Challenge with ID " + challengeId + " not found.")
        );

        challenge.getPlayers().add(currentPlayer);

        challengeRepository.save(challenge);
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

