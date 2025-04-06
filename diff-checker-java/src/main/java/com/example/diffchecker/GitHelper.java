package com.example.diffchecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitHelper {
    private final String repoPath;

    public GitHelper(String repoPath) {
        this.repoPath = repoPath;
    }

    public String getMergeBase(String branchA, String branchB) throws Exception {
        return runGitCommand("merge-base " + branchA + " " + branchB).get(0);
    }

    public List<String> getChangedFiles(String fromCommit, String toBranch) throws Exception {
        return runGitCommand("diff --name-only " + fromCommit + " " + toBranch);
    }

    private List<String> runGitCommand(String args) throws Exception {
        List<String> output = new ArrayList<>();
        
        String[] commandArgs = args.split(" ");
        
        ProcessBuilder builder = new ProcessBuilder("git", "-C", repoPath);
        builder.command(commandArgs);  
        
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line.trim());
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) throw new RuntimeException("Git command failed: " + args);

        return output;
    }
}