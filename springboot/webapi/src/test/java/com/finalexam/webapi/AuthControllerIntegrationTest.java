package com.finalexam.webapi;
import com.finalexam.webapi.payload.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + loginRequest.getUsername() + "\", \"password\": \"" + loginRequest.getPassword() + "\" }"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists())
                .andExpect(jsonPath("$.username").value(loginRequest.getUsername()));
    }

    @Test
    public void whenInvalidInput_thenReturns400() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("invalidpassword");

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + loginRequest.getUsername() + "\", \"password\": \"" + loginRequest.getPassword() + "\" }"));

        // Then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }
}
