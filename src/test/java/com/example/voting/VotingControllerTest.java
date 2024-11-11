package com.example.voting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VotingController.class)
public class VotingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEnterCandidate() throws Exception {
        mockMvc.perform(post("/entercandidate")
                .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidate john registered successfully"));
    }

    @Test
    public void testCastVote() throws Exception {
        // First register a candidate
        mockMvc.perform(post("/entercandidate")
                .param("name", "Alice"));
                
        // Then cast a vote
        mockMvc.perform(post("/castvote")
                .param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(content().string("Vote cast successfully. Current count: 1"));
    }

    @Test
    public void testInvalidCandidate() throws Exception {
        mockMvc.perform(post("/castvote")
                .param("name", "InvalidName"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid candidate name"));
    }

    @Test
    public void testListVotes() throws Exception {
        // Register and vote for a candidate
        mockMvc.perform(post("/entercandidate")
                .param("name", "Bob"));
        mockMvc.perform(post("/castvote")
                .param("name", "Bob"));
                
        mockMvc.perform(get("/listvote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].votes").exists());
    }

    @Test
    public void testGetWinner() throws Exception {
        // Register and vote for candidates
        mockMvc.perform(post("/entercandidate")
                .param("name", "Charlie"));
        mockMvc.perform(post("/castvote")
                .param("name", "Charlie"));
        mockMvc.perform(post("/castvote")
                .param("name", "Charlie"));
                
        mockMvc.perform(get("/getwinner"))
                .andExpect(status().isOk())
                .andExpect(content().string("charlie with 2 votes"));
    }
}