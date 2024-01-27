package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DetailedTaskDto {

    String id;
    String name;
    String description;
    Priority priority;
    Status status;
    LocalDateTime finishDate;
    List<SimpleSubTaskUiDto> subTasks;

}
