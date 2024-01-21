package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.application.TaskService;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskStatusUpdateDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskWsController {

    private final TaskService taskService;
    private final SubTaskService subTaskService;

    @MessageMapping("/tasks/status/update")
    public void updateTaskStatus(TaskStatusUpdateDto message) {
        if ("TASK".equalsIgnoreCase(message.getType())) {
            taskService.updateTaskStatus(message.getId(), message.getNewStatus());
            return;
        }
        subTaskService.updateSubTaskStatus(message.getId(), message.getNewStatus());
    }

    @MessageMapping("/tasks/add")
    @SendTo("/taskmngr/tasks/new")
    public TaskDto addTask(TaskCreationDto message) {
        return taskService.createTask(message);
    }

    @MessageMapping("/tasks")
    @SendTo("/taskmngr/tasks")
    public List<TaskDto> getTasks(String message) {
        return taskService.findAllTasks();
    }

    @MessageMapping("/tasks/update")
    @SendTo("/taskmngr/tasks/update")
    public TaskDto updateTask(TaskUpdateDto task) {
        return taskService.updateTask(task);
    }

    @MessageMapping("/tasks/delete")
    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }

}
