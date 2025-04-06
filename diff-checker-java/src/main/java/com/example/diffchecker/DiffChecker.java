package com.example.diffchecker;

import java.util.*;
import java.util.stream.Collectors;

public class DiffChecker {
    private final GitHelper gitHelper;
    private final GitHubHelper gitHubHelper;

    public DiffChecker(GitHelper gitHelper, GitHubHelper gitHubHelper) {
        this.gitHelper = gitHelper;
        this.gitHubHelper = gitHubHelper;
    }

    public Set<String> getFilesChangedInBothBranches(String localBranchB, String remoteBranchA) throws Exception {
        String mergeBase = gitHelper.getMergeBase(remoteBranchA, localBranchB);
        List<String> localChanges = gitHelper.getChangedFiles(mergeBase, localBranchB);
        Set<String> remoteChanges = gitHubHelper.getChangedFilesSince(remoteBranchA, mergeBase);

        return localChanges.stream()
                .filter(remoteChanges::contains)
                .collect(Collectors.toSet());
    }
}