package com.example.voting;

public class CandidateVotes {
    private String name;
    private int votes;

    public CandidateVotes(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }
}