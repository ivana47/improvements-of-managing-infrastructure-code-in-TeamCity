package com.example.diffchecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class GitHubHelper {
    private final String owner, repo, token;

    public GitHubHelper(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
    }

    public Set<String> getChangedFilesSince(String branch, String baseCommit) throws Exception {
        Set<String> changedFiles = new HashSet<>();
        String url = String.format("https://api.github.com/repos/%s/%s/compare/%s...%s", owner, repo, baseCommit, branch);
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Authorization", "token " + token);
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

        ObjectMapper mapper = new ObjectMapper();  // This line is valid if inside a method
        JsonNode json = mapper.readTree(conn.getInputStream());

        for (JsonNode file : json.get("files")) {
            changedFiles.add(file.get("filename").asText());
        }
        return changedFiles;
    }
}