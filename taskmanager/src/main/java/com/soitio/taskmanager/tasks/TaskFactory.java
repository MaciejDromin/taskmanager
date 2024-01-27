package com.soitio.taskmanager.tasks;

import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.dto.DetailedTaskDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleSubTaskUiDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUiDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto;
import com.soitio.taskmanager.tasks.domain.proj.DetailedTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.SimpleSubTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

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

    public DetailedTaskDto detailedTask(DetailedTaskProj task) {
        return DetailedTaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .priority(task.getPriority())
                .subTasks(task.getSubTasks().stream()
                        .map(this::createSimpleSubTask)
                        .toList())
                .status(task.getStatus())
                .description(task.getDescription())
                .finishDate(task.getFinishDate())
                .build();
    }

    public SimpleTaskUiDto simpleUiTask(SimpleTaskProj task) {
        return SimpleTaskUiDto.builder()
                .id(task.getId())
                .name(task.getName())
                .priority(task.getPriority())
                .subTasks(task.getSubTasks().stream()
                        .map(this::createSimpleSubTask)
                        .toList())
                .status(task.getStatus())
                .build();
    }

    public void updateTask(Task taskToUpdate, TaskUpdateDto task, Goal goal) {
        taskToUpdate.setName(task.getName());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setFinishDate(task.getFinishDate());
        taskToUpdate.setGoal(goal);
    }

    private SimpleSubTaskUiDto createSimpleSubTask(SimpleSubTaskProj subTaskProj) {
        return SimpleSubTaskUiDto.builder()
                .id(subTaskProj.getId())
                .name(subTaskProj.getName())
                .priority(subTaskProj.getPriority())
                .status(subTaskProj.getStatus())
                .build();
    }

}
