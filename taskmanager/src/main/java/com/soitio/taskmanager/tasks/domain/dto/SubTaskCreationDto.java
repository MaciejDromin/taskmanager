package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubTaskCreationDto {

    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime finishDate;
    private String taskId;

}
