package com.soitio.taskmanager.tasks;

import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.SubTask;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class SubTaskFactory {

    public SubTaskDto from(SubTask subTask) {
        return SubTaskDto.builder()
                .id(subTask.getId())
                .name(subTask.getName())
                .description(subTask.getDescription())
                .priority(subTask.getPriority())
                .createdDate(subTask.getCreatedDate())
                .updatedDate(subTask.getUpdatedDate())
                .finishDate(subTask.getFinishDate())
                .status(subTask.getStatus())
                .build();
    }

    public SubTask createSubTask(SubTaskCreationDto subTaskCreationDto, Task task) {
        LocalDateTime creationDate = LocalDateTime.now(ZoneOffset.UTC);
        return SubTask.builder()
                .name(subTaskCreationDto.getName())
                .description(subTaskCreationDto.getDescription())
                .priority(subTaskCreationDto.getPriority())
                .createdDate(creationDate)
                .updatedDate(creationDate)
                .finishDate(subTaskCreationDto.getFinishDate())
                .task(task)
                .status(Status.NOT_STARTED)
                .build();
    }
}
