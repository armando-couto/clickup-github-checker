package com.armandocouto.checker.service;

import com.armandocouto.checker.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.org}")
    private String githubOrg;

    public List<String> getRepos() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // 10 MB
                .build();
        return webClient.get()
                .uri("/orgs/" + githubOrg + "/repos?per_page=100")
                .retrieve()
                .bodyToMono(String.class)
                .map(JsonUtils::extractRepoNames)
                .block();
    }

    public boolean branchExists(String repo, String branch) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // 10 MB
                .build();

        String url = "/repos/" + githubOrg + "/" + repo + "/branches/" + branch;
        return webClient.get()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false)
                .block();
    }
}
