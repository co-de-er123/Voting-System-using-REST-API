// VotingController.java
package com.example.voting;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VotingController {
    // Using ConcurrentHashMap for thread-safe operations
    private final ConcurrentHashMap<String, Integer> votingData = new ConcurrentHashMap<>();

    @PostMapping("/entercandidate")
    public ResponseEntity<String> enterCandidate(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }
        
        String normalizedName = name.toLowerCase().trim();
        if (votingData.putIfAbsent(normalizedName, 0) != null) {
            return ResponseEntity.badRequest().body("Candidate already exists");
        }
        
        return ResponseEntity.ok("Candidate " + normalizedName + " registered successfully");
    }

    @PostMapping("/castvote")
    public ResponseEntity<String> castVote(@RequestParam String name) {
        String normalizedName = name.toLowerCase().trim();
        if (!votingData.containsKey(normalizedName)) {
            return ResponseEntity.badRequest().body("Invalid candidate name");
        }
        
        int updatedCount = votingData.compute(normalizedName, (key, count) -> count + 1);
        return ResponseEntity.ok("Vote cast successfully. Current count: " + updatedCount);
    }

    @GetMapping("/countvote")
    public ResponseEntity<?> countVote(@RequestParam String name) {
        String normalizedName = name.toLowerCase().trim();
        Integer voteCount = votingData.get(normalizedName);
        
        if (voteCount == null) {
            return ResponseEntity.badRequest().body("Invalid candidate name");
        }
        
        return ResponseEntity.ok(voteCount);
    }

    @GetMapping("/listvote")
    public ResponseEntity<List<CandidateVotes>> listVotes() {
        List<CandidateVotes> votes = votingData.entrySet().stream()
            .map(entry -> new CandidateVotes(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/getwinner")
    public ResponseEntity<String> getWinner() {
        if (votingData.isEmpty()) {
            return ResponseEntity.ok("No candidates registered");
        }
        
        Map.Entry<String, Integer> winner = votingData.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);
            
        return ResponseEntity.ok(winner.getKey() + " with " + winner.getValue() + " votes");
    }
}