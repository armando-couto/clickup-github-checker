package com.armandocouto.checker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.webhook-url}")
    private String slackWebhookUrl;

    public void sendMessage(String message) {
        WebClient webClient = WebClient.builder().build();

        webClient.post()
                .uri(slackWebhookUrl)
                .bodyValue("{\"text\": \"" + message + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Erro ao enviar mensagem para o Slack", e))
                .onErrorResume(e -> Mono.empty())
                .block();
    }
}
