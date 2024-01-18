package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/subtasks")
@RequiredArgsConstructor
public class SubTaskController {

    private final SubTaskService subTaskService;

    @GetMapping
    public List<SubTaskDto> findAllTasks() {
        return subTaskService.findAllSubTasks();
    }

    @PostMapping
    public SubTaskDto createTask(@RequestBody SubTaskCreationDto subTaskCreationDto) {
        return subTaskService.createSubTask(subTaskCreationDto);
    }

}
