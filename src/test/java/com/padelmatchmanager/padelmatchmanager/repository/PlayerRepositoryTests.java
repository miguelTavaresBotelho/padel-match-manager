package com.padelmatchmanager.padelmatchmanager.repository;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlayerRepositoryTests {

    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void testFindByUsername() {
        // Given
        Player player1 = new Player();
        player1.setUsername("user1");
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setUsername("user2");
        playerRepository.save(player2);

        // When
        Optional<Player> foundPlayer1 = playerRepository.findByUsername("user1");
        Optional<Player> foundPlayer2 = playerRepository.findByUsername("user2");
        Optional<Player> nonExistentPlayer = playerRepository.findByUsername("nonexistent");

        // Then
        assertThat(foundPlayer1).isPresent();
        assertThat(foundPlayer1.get().getUsername()).isEqualTo("user1");

        assertThat(foundPlayer2).isPresent();
        assertThat(foundPlayer2.get().getUsername()).isEqualTo("user2");

        assertThat(nonExistentPlayer).isEmpty();
    }

    @Test
    public void testFindByUsernameContainingIgnoreCase() {
        // Given
        Player player1 = new Player();
        player1.setUsername("JohnDoe");
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setUsername("JaneDoe");
        playerRepository.save(player2);

        // When
        List<Player> playersContainingDoe = playerRepository.findByUsernameContainingIgnoreCase("Doe");
        List<Player> playersContainingjohn = playerRepository.findByUsernameContainingIgnoreCase("john");

        // Then
        assertThat(playersContainingDoe).hasSize(2);
        assertThat(playersContainingjohn).hasSize(1);
        assertThat(playersContainingjohn.get(0).getUsername()).isEqualTo("JohnDoe");
    }

}

