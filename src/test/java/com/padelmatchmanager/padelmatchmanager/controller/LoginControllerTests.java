package com.padelmatchmanager.padelmatchmanager.controller;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowCustomLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    public void testLoginError() throws Exception {
        mockMvc.perform(get("/login-error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}

