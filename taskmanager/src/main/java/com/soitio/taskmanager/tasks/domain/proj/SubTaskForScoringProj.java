package com.soitio.taskmanager.tasks.domain.proj;

import com.soitio.taskmanager.tasks.domain.Priority;

import java.time.LocalDateTime;

public interface SubTaskForScoringProj {

    String getId();
    Priority getPriority();
    LocalDateTime getFinishDate();

}
