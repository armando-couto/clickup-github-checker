package com.armandocouto.checker.controller;

import com.armandocouto.checker.service.ClickUpService;
import com.armandocouto.checker.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class CheckerController {

    private final ClickUpService clickUpService;
    private final GitHubService gitHubService;

    @GetMapping("/check")
    public Map<String, List<String>> checkBranches() {
        List<String> tasks = clickUpService.getDoneTasks();
        List<String> repos = gitHubService.getRepos();

        Map<String, List<String>> openBranches = new HashMap<>();

        for (String task : tasks) {
            List<String> branchesOpen = new ArrayList<>();
            for (String repo : repos) {
                if (gitHubService.branchExists(repo, task)) {
                    branchesOpen.add(repo);
                }
            }
            if (!branchesOpen.isEmpty()) {
                openBranches.put(task, branchesOpen);
            }
        }
        return openBranches;
    }
}
