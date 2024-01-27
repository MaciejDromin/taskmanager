package com.soitio.taskmanager.goals.domain;

import com.soitio.taskmanager.tasks.domain.Status;
import java.time.LocalDateTime;
import java.util.List;

public interface GoalProgressProjection {

    String getId();

    String getName();

    LocalDateTime getCreationDate();

    LocalDateTime getFinishDate();

    List<TaskForProgressProj> getTasks();

    interface TaskForProgressProj {

        String getId();

        Status getStatus();

    }

}
