package com.armandocouto.checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClickupGithubCheckerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickupGithubCheckerApplication.class, args);
    }
}
