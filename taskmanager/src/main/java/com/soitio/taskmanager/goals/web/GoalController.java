package com.soitio.taskmanager.goals.web;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public List<GoalDto> findAllGoals() {
        return goalService.findAllGoals();
    }

    @PostMapping
    public GoalDto createGoal(@RequestBody GoalDto goalDto) {
        return goalService.createGoal(goalDto);
    }

    @GetMapping("/tasks/{taskId}")
    public GoalDto getGoalByTaskId(@PathVariable String taskId) {
        return goalService.getGoalByTaskId(taskId);
    }

}
