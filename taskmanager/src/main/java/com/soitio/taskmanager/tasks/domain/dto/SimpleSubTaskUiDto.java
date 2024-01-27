package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimpleSubTaskUiDto {

    String id;
    String name;
    Priority priority;
    Status status;

}
