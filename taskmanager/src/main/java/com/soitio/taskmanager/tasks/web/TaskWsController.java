package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.application.TaskService;
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TaskWsController {

    private final TaskService taskService;
    private final SubTaskService subTaskService;

    @MessageMapping("/tasks/update")
    public void getDailyPlan(TaskUpdateDto message) {
        if ("TASK".equalsIgnoreCase(message.getType())) {
            taskService.updateTaskStatus(message.getId(), message.getNewStatus());
            return;
        }
        subTaskService.updateSubTaskStatus(message.getId(), message.getNewStatus());
    }

}
