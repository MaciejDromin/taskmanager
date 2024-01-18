package com.soitio.taskmanager.scoring.web;

import com.soitio.taskmanager.scoring.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debug")
public class ScoringDebugController {

    private final ScoringService scoringService;

    @GetMapping
    public void test() {
        scoringService.calculateTodayScores();
    }
}
