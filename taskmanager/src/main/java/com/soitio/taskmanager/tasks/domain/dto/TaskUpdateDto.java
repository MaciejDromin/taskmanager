package com.soitio.taskmanager.tasks.domain.dto;

import com.soitio.taskmanager.tasks.domain.Status;
import lombok.Value;

@Value
public class TaskUpdateDto {

    String id;
    String type;
    Status newStatus;

}
