package com.soitio.taskmanager.scoring;

import com.soitio.taskmanager.scoring.domain.TaskType;

public record TaskScore(long score, String taskId, TaskType taskType) {

}
