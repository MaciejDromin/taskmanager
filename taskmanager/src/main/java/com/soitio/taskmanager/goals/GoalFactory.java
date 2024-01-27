package com.soitio.taskmanager.goals;

import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.goals.domain.GoalProgressProjection;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import com.soitio.taskmanager.goals.domain.dto.GoalWithProgressDto;
import com.soitio.taskmanager.tasks.domain.Status;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GoalFactory {

    public GoalDto from(Goal goal) {
        return GoalDto.builder()
                .id(goal.getId())
                .name(goal.getName())
                .creationDate(goal.getCreationDate())
                .finishDate(goal.getFinishDate())
                .build();
    }

    public Goal createGoal(GoalDto goalCreation) {
        return Goal.builder()
                .name(goalCreation.getName())
                .creationDate(LocalDateTime.now(ZoneOffset.UTC))
                .finishDate(goalCreation.getFinishDate())
                .build();
    }

    public void updateGoal(Goal goalToUpdate, GoalDto goal) {
        goalToUpdate.setName(goal.getName());
        goalToUpdate.setFinishDate(goal.getFinishDate());
    }

    public GoalWithProgressDto createGoalWithProgress(GoalProgressProjection goalProgressProjection) {
        return GoalWithProgressDto.builder()
                .id(goalProgressProjection.getId())
                .name(goalProgressProjection.getName())
                .creationDate(goalProgressProjection.getCreationDate())
                .finishDate(goalProgressProjection.getFinishDate())
                .progress(calculateProgress(goalProgressProjection.getTasks()))
                .build();
    }

    private int calculateProgress(List<GoalProgressProjection.TaskForProgressProj> tasks) {
        long totalTasks = tasks.size();
        if (totalTasks == 0) return 0;
        long finishedTasks = tasks.stream()
                .filter(task -> task.getStatus() == Status.FINISHED)
                .count();

        return (int) (finishedTasks * (100 / totalTasks));
    }

}
