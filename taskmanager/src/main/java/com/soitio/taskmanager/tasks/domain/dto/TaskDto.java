package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {

    private String id;

    private String name;

    private String description;

    private Priority priority;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime finishDate;
    private Status status;

}
