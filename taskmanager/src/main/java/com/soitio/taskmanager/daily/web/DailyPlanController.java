package com.soitio.taskmanager.daily.web;

import com.soitio.taskmanager.daily.application.DailyPlanService;
import com.soitio.taskmanager.daily.domain.dto.DailyPlanSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-plan")
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;

    @GetMapping
    public DailyPlanSimpleDto getDailyPlan() {
        return dailyPlanService.getDailyPlan();
    }

}
