package com.soitio.taskmanager.goals.application;

import com.soitio.taskmanager.goals.GoalFactory;
import com.soitio.taskmanager.goals.application.port.GoalRepository;
import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalFactory goalFactory;

    public GoalDto createGoal(GoalDto goal) {
        return goalFactory.from(goalRepository.save(goalFactory.createGoal(goal)));
    }

    public List<GoalDto> findAllGoals() {
        return goalRepository.findAll()
                .stream()
                .map(goalFactory::from)
                .toList();
    }

    public Goal getGoalById(String goalId) {
        return goalRepository.getReferenceById(goalId);
    }
}
