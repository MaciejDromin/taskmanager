package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskUpdateDto {

    String id;
    String name;
    String description;
    Priority priority;
    LocalDateTime finishDate;
    String goalId;

}
