package com.soitio.taskmanager.tasks.domain.proj;

import com.soitio.taskmanager.tasks.domain.Priority;
import java.time.LocalDateTime;

public interface TaskForScoringProj {

    String getId();

    Priority getPriority();

    LocalDateTime getFinishDate();

}
