package com.soitio.taskmanager.scoring.domain;

import com.soitio.taskmanager.tasks.domain.Priority;
import java.time.LocalDateTime;

public record ScorableTask(String id, Priority priority, LocalDateTime finishDate, TaskType taskType) {
}
