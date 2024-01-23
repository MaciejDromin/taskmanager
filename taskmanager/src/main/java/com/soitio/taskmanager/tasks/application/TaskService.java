package com.soitio.taskmanager.tasks.application;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.tasks.TaskFactory;
import com.soitio.taskmanager.tasks.application.port.TaskRepository;
import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.dto.DetailedTaskDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto;
import com.soitio.taskmanager.tasks.domain.proj.DetailedTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final static List<String> FINISHED_ACTIONS = List.of("Edit", "Clone");
    private final static List<String> DEFAULT_ACTIONS = List.of("Edit", "Clone", "Add SubTask");

    private final TaskRepository taskRepository;
    private final GoalService goalService;
    private final TaskFactory taskFactory;

    public TaskDto createTask(TaskCreationDto taskCreation) {
        return taskFactory.from(taskRepository.save(taskFactory.createTask(taskCreation,
                goalService.getGoalById(taskCreation.getGoalId()))));
    }

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskFactory::from)
                .toList();
    }

    public List<TaskForScoringProj> findAllPlainTasksForScoring() {
        return taskRepository.findAllForScoringByStatusNot(Status.FINISHED);
    }

    public Task getTaskById(String taskId) {
        return taskRepository.getReferenceById(taskId);
    }

    @Transactional
    public Set<SimpleTaskUIDto> findSimpleTasks(List<String> taskIds) {
        List<SimpleTaskProj> tasks = taskRepository.findAllProjByIdIn(taskIds, SimpleTaskProj.class);
        return tasks.stream()
                .map(taskFactory::simpleUITask)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<SimpleTaskUIDto> findSimpleTasksBySubTasksIds(List<String> subTaskIds) {
        return taskRepository.findAllBySubTasksIdIn(subTaskIds).stream()
                .map(taskFactory::simpleUITask)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void updateTaskStatus(String id, Status newStatus) {
        var task = taskRepository.getReferenceById(id);
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskDto updateTask(TaskUpdateDto task) {
        var taskToUpdate = taskRepository.getReferenceById(task.getId());
        return taskFactory.from(
                taskRepository.save(
                        taskFactory.updateTask(taskToUpdate, task, task.getGoalId() == null
                                ? null : goalService.getGoalById(task.getGoalId()))));
    }

    @Transactional
    public DetailedTaskDto getDetailedTask(String taskId) {
        List<DetailedTaskProj> tasks = taskRepository.findAllProjByIdIn(List.of(taskId), DetailedTaskProj.class);
        return tasks.stream()
                .map(taskFactory::detailedTask)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Could not find task with id %s".formatted(tasks)));
    }

    public List<String> determineAvailableActions(String taskId) {
        if (Status.FINISHED == taskRepository.getProjById(taskId).getStatus()) {
            return FINISHED_ACTIONS;
        }
        return DEFAULT_ACTIONS;
    }

}
