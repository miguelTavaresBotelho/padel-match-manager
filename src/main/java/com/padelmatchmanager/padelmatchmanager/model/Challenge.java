package com.padelmatchmanager.padelmatchmanager.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;
    private String club;
    private LocalDateTime startTime;
    private Duration playTime;

    @Enumerated(EnumType.STRING)
    private ChallengeState state;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

    private LocalDateTime creationDate;

    public Challenge() {
        // Default constructor required by JPA
    }

    public Challenge(String level, String club, LocalDateTime startTime, Duration playTime, ChallengeState state, List<Player> players, LocalDateTime creationDate) {
        this.level = level;
        this.club = club;
        this.startTime = startTime;
        this.playTime = playTime;
        this.state = state;
        this.players = players;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Duration playTime) {
        this.playTime = playTime;
    }

    public ChallengeState getState() {
        return state;
    }

    public void setState(ChallengeState state) {
        this.state = state;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


}

