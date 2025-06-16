package com.armandocouto.checker.controller;

import com.armandocouto.checker.job.CheckerJob;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckerController {

    private final CheckerJob checkerJob;

    @GetMapping("/check")
    public String checkManually() {
        checkerJob.runCheck();
        return "Verificação executada. Veja os logs.";
    }
}
