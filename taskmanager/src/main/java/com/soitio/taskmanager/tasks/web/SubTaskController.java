package com.soitio.taskmanager.tasks.web;

import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{subTaskId}")
    public SubTaskDto getTaskById(@PathVariable String subTaskId) {
        return subTaskService.getDetailedSubTask(subTaskId);
    }

    @GetMapping("/{subTaskId}/actions")
    public List<String> getAvailableActionsForTask(@PathVariable String subTaskId) {
        return subTaskService.determineAvailableActions(subTaskId);
    }

}
