package com.example.diffchecker;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DiffCheckerTest {

    @Test
    public void testChangedFilesInBothBranches() throws Exception {
        GitHelper gitHelper = new GitHelper("/path/to/local/repo");
        GitHubHelper gitHubHelper = new GitHubHelper("username", "repo", "your_github_token");

        DiffChecker diffChecker = new DiffChecker(gitHelper, gitHubHelper);
        Set<String> changed = diffChecker.getFilesChangedInBothBranches("branchB", "branchA");

        assertNotNull(changed);
        System.out.println("Changed in both branches: " + changed);
    }
}