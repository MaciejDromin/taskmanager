package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskCreationDto {

    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime finishDate;
    private String goalId;

}
