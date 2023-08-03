package com.padelmatchmanager.padelmatchmanager.controller;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testRegisterPlayer_Success() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(playerService, times(1)).savePlayer(any(Player.class));
    }
}
