package com.soitio.taskmanager.daily.web;

import com.soitio.taskmanager.daily.application.DailyPlanService;
import com.soitio.taskmanager.daily.domain.dto.DailyPlanSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DailyWsController {

    private final DailyPlanService dailyPlanService;

    @MessageMapping("/daily")
    @SendTo("/taskmngr/daily")
    public DailyPlanSimpleDto getDailyPlan(String message) {
        return dailyPlanService.getDailyPlan();
    }

}
