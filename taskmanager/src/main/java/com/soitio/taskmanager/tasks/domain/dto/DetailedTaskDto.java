package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class DetailedTaskDto {

    String id;
    String name;
    String description;
    Priority priority;
    Status status;
    LocalDateTime finishDate;
    List<SimpleSubTaskUIDto> subTasks;

}
