package com.soitio.taskmanager.goals.web;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import java.util.List;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    @Timed("endpoints.goals")
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
