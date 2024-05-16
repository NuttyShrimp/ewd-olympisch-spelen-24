package org.hogent.olympisch_spelen_24.controllers;

import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.hogent.olympisch_spelen_24.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(LoginController.class)
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
class LoginControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginGet() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    void loginGetWithError() throws Exception {
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Invalid username or password."));
    }

    @Test
    void loginGetWithMessage() throws Exception {
        mockMvc.perform(get("/login").param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "You have been logged out successfully."));
    }
}
