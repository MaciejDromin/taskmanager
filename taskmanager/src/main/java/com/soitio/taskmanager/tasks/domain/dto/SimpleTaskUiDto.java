package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimpleTaskUiDto {

    String id;
    String name;
    Priority priority;
    Status status;
    List<SimpleSubTaskUiDto> subTasks;

}
