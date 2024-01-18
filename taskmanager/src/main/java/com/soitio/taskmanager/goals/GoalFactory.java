package com.soitio.taskmanager.goals;

import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.goals.domain.dto.GoalDto;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public Goal to(GoalDto goal) {
        return Goal.builder()
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

}
