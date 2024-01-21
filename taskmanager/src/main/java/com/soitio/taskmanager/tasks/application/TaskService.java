package com.soitio.taskmanager.tasks.application;

import com.soitio.taskmanager.goals.application.GoalService;
import com.soitio.taskmanager.tasks.TaskFactory;
import com.soitio.taskmanager.tasks.application.port.TaskRepository;
import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskDto;
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto;
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

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
        return taskRepository.findAllProjByIdIn(taskIds).stream()
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
}
