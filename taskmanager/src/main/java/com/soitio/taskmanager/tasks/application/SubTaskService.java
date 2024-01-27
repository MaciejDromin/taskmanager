package com.soitio.taskmanager.tasks.application;

import com.soitio.taskmanager.tasks.SubTaskFactory;
import com.soitio.taskmanager.tasks.application.port.SubTaskRepository;
import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.SubTask;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto;
import com.soitio.taskmanager.tasks.domain.proj.SubTaskForScoringProj;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubTaskService {

    private static final List<String> DEFAULT_ACTIONS = List.of("Edit", "Clone");

    private final SubTaskRepository subTaskRepository;
    private final TaskService taskService;
    private final SubTaskFactory subTaskFactory;

    public SubTaskDto createSubTask(SubTaskCreationDto subTaskCreation) {
        return subTaskFactory.from(subTaskRepository.save(subTaskFactory.createSubTask(subTaskCreation,
                taskService.getTaskById(subTaskCreation.getTaskId()))));
    }

    public List<SubTaskDto> findAllSubTasks() {
        return subTaskRepository.findAll().stream()
                .map(subTaskFactory::from)
                .toList();
    }

    public List<SubTaskForScoringProj> findAllPlainSubTasksForScoring() {
        return subTaskRepository.findAllForScoringByStatusNot(Status.FINISHED);
    }

    @Transactional
    public void updateSubTaskStatus(String id, Status newStatus) {
        var subTask = subTaskRepository.getReferenceById(id);
        subTask.setStatus(newStatus);
        subTaskRepository.save(subTask);
    }

    @Transactional
    public SubTaskDto updateSubTask(SubTaskDto subTaskDto) {
        var subTask = subTaskRepository.getReferenceById(subTaskDto.getId());
        subTaskFactory.updateSubTask(subTask, subTaskDto);
        return subTaskFactory.from(subTaskRepository.save(subTask));
    }

    @Transactional
    public SubTaskDto getDetailedSubTask(String subTaskId) {
        SubTask task = subTaskRepository.getReferenceById(subTaskId);
        return subTaskFactory.from(task);
    }

    public List<String> determineAvailableActions(String subTaskId) {
        return DEFAULT_ACTIONS;
    }

}
