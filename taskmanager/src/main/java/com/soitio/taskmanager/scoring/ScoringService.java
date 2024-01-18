package com.soitio.taskmanager.scoring;

import com.soitio.taskmanager.scoring.domain.ScorableTask;
import com.soitio.taskmanager.scoring.domain.TaskType;
import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.application.TaskService;
import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.proj.SubTaskForScoringProj;
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class ScoringService {

    private final TaskService taskService;
    private final SubTaskService subTaskService;

    public PriorityQueue<TaskScore> calculateTodayScores() {
        List<ScorableTask> scorableTasks = new ArrayList<>();

        convertTasksToScorableTasks(taskService.findAllPlainTasksForScoring(), scorableTasks);
        convertSubTasksToScorableTasks(subTaskService.findAllPlainSubTasksForScoring(), scorableTasks);

        PriorityQueue<TaskScore> pq = new PriorityQueue<>(Comparator.comparingLong(TaskScore::score).reversed());
        pq.addAll(calculateScores(scorableTasks));
        return pq;
    }

    private List<TaskScore> calculateScores(List<ScorableTask> scorableTasks) {
        return scorableTasks.parallelStream()
                .map(task -> new TaskScore(calculatePriorityScore(task.priority()) + calculateDateScore(task.finishDate()),
                        task.id(), task.taskType()))
                .toList();
    }

    private long calculateDateScore(LocalDateTime finishDate) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        long daysBetween = ChronoUnit.DAYS.between(now, finishDate);

        if (daysBetween > 7) {
            return 0L;
        }
        if (daysBetween > 4) {
            return 20L;
        }
        if (daysBetween > 1) {
            return 50L;
        }
        return 100L;
    }

    private long calculatePriorityScore(Priority priority) {
        return switch (priority) {
            case TRIVIAL -> 10L;
            case LOW -> 30L;
            case MEDIUM -> 50L;
            case HIGH -> 100L;
        };
    }

    private void convertTasksToScorableTasks(List<TaskForScoringProj> allTasks, List<ScorableTask> scorableTasks) {
        scorableTasks.addAll(allTasks.parallelStream()
                .map(task -> new ScorableTask(task.getId(), task.getPriority(), task.getFinishDate(), TaskType.TASK))
                .toList());
    }

    private void convertSubTasksToScorableTasks(List<SubTaskForScoringProj> allSubTasks, List<ScorableTask> scorableTasks) {
        scorableTasks.addAll(allSubTasks.parallelStream()
                .map(task -> new ScorableTask(task.getId(), task.getPriority(), task.getFinishDate(), TaskType.SUBTASK))
                .toList());
    }

}
