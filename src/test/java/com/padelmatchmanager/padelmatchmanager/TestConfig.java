package com.padelmatchmanager.padelmatchmanager;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@TestConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
public class TestConfig implements WebMvcConfigurer {

    // Beans

}
