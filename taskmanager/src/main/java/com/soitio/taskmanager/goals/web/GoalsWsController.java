package com.soitio.taskmanager.goals.web;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import com.soitio.taskmanager.goals.domain.dto.GoalWithProgressDto;
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
    public List<GoalWithProgressDto> initializeGoals(String message) {
        return goalService.findAllGoalsWithProgress();
    }

    @MessageMapping("/goals/add")
    @SendTo("/taskmngr/goals/new")
    public GoalDto addGoal(GoalDto goal) {
        return goalService.createGoal(goal);
    }

    @MessageMapping("/goals/update")
    @SendTo("/taskmngr/goals/update")
    public GoalDto updateGoal(GoalDto goal) {
        return goalService.updateGoal(goal);
    }

    @MessageMapping("/goals/delete")
    public void deleteGoal(String goalId) {
        goalService.deleteGoal(goalId);
    }

}
