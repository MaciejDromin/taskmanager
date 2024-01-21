package com.soitio.taskmanager.goals.application;

import com.soitio.taskmanager.goals.GoalFactory;
import com.soitio.taskmanager.goals.application.port.GoalRepository;
import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import com.soitio.taskmanager.goals.domain.dto.GoalWithProgressDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalFactory goalFactory;

    @Transactional
    public GoalDto createGoal(GoalDto goal) {
        var createdGoal = goalRepository.save(goalFactory.createGoal(goal));
        return goalFactory.from(createdGoal);
    }

    public List<GoalDto> findAllGoals() {
        return goalRepository.findAll()
                .stream()
                .map(goalFactory::from)
                .toList();
    }

    @Transactional
    public List<GoalWithProgressDto> findAllGoalsWithProgress() {
        return goalRepository.findAllProjBy()
                .stream()
                .map(goalFactory::createGoalWithProgress)
                .toList();
    }

    public Goal getGoalById(String goalId) {
        return goalRepository.getReferenceById(goalId);
    }

    @Transactional
    public GoalDto updateGoal(GoalDto goal) {
        var goalToUpdate = goalRepository.getReferenceById(goal.getId());
        return goalFactory.from(goalRepository.save(goalFactory.updateGoal(goalToUpdate, goal)));
    }

    public void deleteGoal(String goalId) {
        goalRepository.deleteById(goalId);
    }

    public GoalDto getGoalByTaskId(String taskId) {
        return goalRepository.findGoalByTasksIdIn(List.of(taskId))
                .map(goalFactory::from)
                .orElse(null);
    }
}
