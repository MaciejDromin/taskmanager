package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubTaskWsController {

    private final SubTaskService subTaskService;

    @MessageMapping("/subtasks/add")
    @SendTo("/taskmngr/subtasks/new")
    public SubTaskDto addTask(SubTaskCreationDto message) {
        return subTaskService.createSubTask(message);
    }

    @MessageMapping("/subtasks/update")
    @SendTo("/taskmngr/subtasks/update")
    public SubTaskDto updateSubTask(SubTaskDto subTaskDto) {
        return subTaskService.updateSubTask(subTaskDto);
    }

}
