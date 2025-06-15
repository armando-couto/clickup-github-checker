package com.armandocouto.checker.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> extractTaskCustomId(String json) {
        List<String> tasks = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            JsonNode taskNodes = node.get("tasks");
            if (taskNodes != null && taskNodes.isArray()) {
                for (JsonNode task : taskNodes) {
                    // Prioriza custom_id, se não tiver, usa id
                    String name = task.hasNonNull("custom_id") && !task.get("custom_id").asText().isEmpty()
                            ? task.get("custom_id").asText()
                            : task.get("id").asText();

                    // Normaliza o nome da task para o nome da branch
                    String branchName = name.trim()
                            .replaceAll("[^a-zA-Z0-9-_]", "-")
                            .toLowerCase();

                    tasks.add(branchName.toUpperCase());
                    tasks.add(branchName.toLowerCase());
                    tasks.add("feature/" + branchName.toUpperCase());
                    tasks.add("feature/" + branchName.toLowerCase());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static List<String> extractRepoNames(String json) {
        List<String> repos = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            if (node.isArray()) {
                for (JsonNode repo : node) {
                    if (repo.has("name")) {
                        repos.add(repo.get("name").asText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repos;
    }
}
