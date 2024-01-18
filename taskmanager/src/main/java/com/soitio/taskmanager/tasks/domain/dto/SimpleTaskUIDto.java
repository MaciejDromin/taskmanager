package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SimpleTaskUIDto {

    String id;
    String name;
    Priority priority;
    Status status;
    List<SimpleSubTaskUIDto> subTasks;

}
