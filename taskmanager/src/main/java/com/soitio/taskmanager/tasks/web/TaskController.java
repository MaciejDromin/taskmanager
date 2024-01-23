package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.TaskService;
import com.soitio.taskmanager.tasks.domain.dto.DetailedTaskDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> findAllTasks() {
        return taskService.findAllTasks();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskCreationDto taskCreation) {
        return taskService.createTask(taskCreation);
    }

    @GetMapping("/{taskId}")
    public DetailedTaskDto getTaskById(@PathVariable String taskId) {
        return taskService.getDetailedTask(taskId);
    }

    @GetMapping("/{taskId}/actions")
    public List<String> getAvailableActionsForTask(@PathVariable String taskId) {
        return taskService.determineAvailableActions(taskId);
    }

}
