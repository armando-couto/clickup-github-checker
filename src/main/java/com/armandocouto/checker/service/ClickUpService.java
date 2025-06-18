package com.armandocouto.checker.service;

import com.armandocouto.checker.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickUpService {

    @Value("${clickup.token}")
    private String clickupToken;

    @Value("${clickup.team-id}")
    private String teamId;

    @Value("${clickup.space-id}")
    private String spaceId;

    public List<String> getDoneTasks() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.clickup.com/api/v2")
                .defaultHeader("Authorization", clickupToken)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // 10 MB
                .build();

        int page = 0;
        boolean hasMore = true;
        List<String> allTasks = new ArrayList<>();

        while (hasMore) {
            final int currentPage = page; // variável imutável usada na lambda

            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/team/" + teamId + "/task")
                            .queryParam("statuses[]", "done")
                            .queryParam("statuses[]", "in production")
                            .queryParam("space_ids[]", spaceId)
                            .queryParam("page", currentPage)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            List<String> tasksPage = JsonUtils.extractTaskCustomId(response);
            allTasks.addAll(tasksPage);

            hasMore = checkHasMore(response);
            page++;
        }

        return allTasks;
    }

    private boolean checkHasMore(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode hasMoreNode = root.get("has_more");
            return hasMoreNode != null && hasMoreNode.asBoolean(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
