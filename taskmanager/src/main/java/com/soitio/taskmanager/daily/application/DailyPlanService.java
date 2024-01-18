package com.soitio.taskmanager.daily.application;

import com.soitio.taskmanager.daily.DailyPlanFactory;
import com.soitio.taskmanager.daily.application.port.DailyPlanRepository;
import com.soitio.taskmanager.daily.domain.DailyPlan;
import com.soitio.taskmanager.daily.domain.dto.DailyPlanSimpleDto;
import com.soitio.taskmanager.scoring.ScoringService;
import com.soitio.taskmanager.scoring.TaskScore;
import com.soitio.taskmanager.scoring.config.ScoringConfig;
import com.soitio.taskmanager.scoring.domain.TaskType;
import com.soitio.taskmanager.tasks.application.SubTaskService;
import com.soitio.taskmanager.tasks.application.TaskService;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DailyPlanService {

    private final DailyPlanRepository dailyPlanRepository;
    private final ScoringConfig scoringConfig;
    private final ScoringService scoringService;
    private final TaskService taskService;
    private final DailyPlanFactory dailyPlanFactory;

    @Transactional
    public DailyPlanSimpleDto getDailyPlan() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        Optional<DailyPlan> dailyPlanOpt = dailyPlanRepository.findByEffectiveDateBetween(
                now.with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay()),
                now.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()));

        if (dailyPlanOpt.isPresent()) {
            DailyPlan dailyPlan = dailyPlanOpt.get();

            Set<SimpleTaskUIDto> simpleTasks = taskService.findSimpleTasks(dailyPlan.getDailyTaskIds());
            Set<SimpleTaskUIDto> simpleTasksWithSubTasks = taskService.findSimpleTasksBySubTasksIds(dailyPlan.getDailySubTaskIds());

            return dailyPlanFactory.from(dailyPlan, Stream.concat(simpleTasks.stream(), simpleTasksWithSubTasks.stream()).collect(Collectors.toSet()));
        }

        PriorityQueue<TaskScore> prioritizedTasks = scoringService.calculateTodayScores();

        List<TaskScore> taskScoreLimited = new ArrayList<>();

        for (int i = 0;i < scoringConfig.limit();i++) {
            taskScoreLimited.add(prioritizedTasks.poll());
        }

        List<String> taskIds = taskScoreLimited.parallelStream()
                .filter(taskScore -> taskScore.taskType() == TaskType.TASK)
                .map(TaskScore::taskId)
                .toList();

        List<String> subTaskIds = taskScoreLimited.parallelStream()
                .filter(taskScore -> taskScore.taskType() == TaskType.SUBTASK)
                .map(TaskScore::taskId)
                .toList();

        Set<SimpleTaskUIDto> simpleTasks = taskService.findSimpleTasks(taskIds);
        Set<SimpleTaskUIDto> simpleTasksWithSubTasks = taskService.findSimpleTasksBySubTasksIds(subTaskIds);

        return dailyPlanFactory.from(dailyPlanRepository.save(DailyPlan.builder()
                .effectiveDate(now)
                .dailyTaskIds(taskIds)
                .dailySubTaskIds(subTaskIds)
                .build()), Stream.concat(simpleTasks.stream(), simpleTasksWithSubTasks.stream()).collect(Collectors.toSet()));
    }

}
