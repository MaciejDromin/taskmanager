package com.soitio.taskmanager.goals.web;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoalsWsController {

    private final GoalService goalService;

    @MessageMapping("/goals")
    @SendTo("/taskmngr/goals")
    public List<GoalDto> initializeGoals(String message) {
        return goalService.findAllGoals();
    }

    @MessageMapping("/goals/add")
    @SendTo("/taskmngr/goals/new")
    public GoalDto initializeGoals(GoalDto goal) {
        return goalService.createGoal(goal);
    }

}
