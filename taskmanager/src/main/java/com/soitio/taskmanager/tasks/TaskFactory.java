package com.soitio.taskmanager.tasks;

import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.dto.SimpleSubTaskUIDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import com.soitio.taskmanager.tasks.domain.proj.SimpleSubTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TaskFactory {

    public TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .createdDate(task.getCreatedDate())
                .updatedDate(task.getUpdatedDate())
                .finishDate(task.getFinishDate())
                .status(task.getStatus())
                .build();
    }

    public Task createTask(TaskCreationDto taskCreation, Goal goal) {
        LocalDateTime creationDate = LocalDateTime.now(ZoneOffset.UTC);
        return Task.builder()
                .name(taskCreation.getName())
                .description(taskCreation.getDescription())
                .priority(taskCreation.getPriority())
                .createdDate(creationDate)
                .updatedDate(creationDate)
                .finishDate(taskCreation.getFinishDate())
                .goal(goal)
                .status(Status.NOT_STARTED)
                .build();
    }

    public SimpleTaskUIDto simpleUITask(SimpleTaskProj task) {
        return SimpleTaskUIDto.builder()
                .id(task.getId())
                .name(task.getName())
                .priority(task.getPriority())
                .subTasks(task.getSubTasks().stream()
                        .map(this::createSimpleSubTask)
                        .toList())
                .status(task.getStatus())
                .build();
    }

    private SimpleSubTaskUIDto createSimpleSubTask(SimpleSubTaskProj subTaskProj) {
        return SimpleSubTaskUIDto.builder()
                .id(subTaskProj.getId())
                .name(subTaskProj.getName())
                .priority(subTaskProj.getPriority())
                .status(subTaskProj.getStatus())
                .build();
    }

}
