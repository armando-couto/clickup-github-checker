package com.armandocouto.checker.job;

import com.armandocouto.checker.service.ClickUpService;
import com.armandocouto.checker.service.GitHubService;
import com.armandocouto.checker.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckerJob {

    private final ClickUpService clickUpService;
    private final GitHubService gitHubService;
    private final SlackService slackService;

    // Executando verificação de branches (somente de segunda a sexta, a cada hora)
    @Scheduled(cron = "0 0 * * * MON-FRI")
    public void runCheck() {
        log.info("Iniciando verificação de branches abertas...");

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

        if (!openBranches.isEmpty()) {
            StringBuilder slackMessage = new StringBuilder();
            slackMessage.append(":warning: *Branches abertas encontradas para tarefas DONE:*\n");

            openBranches.forEach((task, reposList) -> {
                slackMessage.append("• *Task:* `").append(task).append("`\n");
                slackMessage.append("  ↳ *Repos:* ").append(String.join(", ", reposList)).append("\n");
            });

            slackService.sendMessage(slackMessage.toString());
            log.info("Mensagem enviada para o Slack.");
        } else {
            log.info("✅ Nenhuma branch em aberto encontrada para tarefas DONE/IN PRODUCTION.");
        }
    }
}
