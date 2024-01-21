package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TaskUpdateDto {

    String id;
    String name;
    String description;
    Priority priority;
    LocalDateTime finishDate;
    String goalId;

}
