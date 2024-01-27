package com.soitio.taskmanager.scoring

import com.soitio.taskmanager.scoring.domain.TaskType
import com.soitio.taskmanager.tasks.application.SubTaskService
import com.soitio.taskmanager.tasks.application.TaskService
import com.soitio.taskmanager.tasks.domain.Priority
import com.soitio.taskmanager.tasks.domain.proj.SubTaskForScoringProj
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import java.time.LocalDateTime

class ScoringServiceSpec extends Specification {

    TaskService taskService = Mock()
    SubTaskService subTaskService = Mock()

    @Subject
    ScoringService scoringService = new ScoringService(taskService, subTaskService)

    def setupSpec() {
        initializeTaskMocks()
        initializeSubTaskMocks()
    }

    @Unroll
    def "should properly calculate scores"() {
        given: "mock dependencies"
        taskService.findAllPlainTasksForScoring() >> tasksForScoring
        subTaskService.findAllPlainSubTasksForScoring() >> subTasksForScoring

        when:
        def result = scoringService.calculateTodayScores()

        then:
        result.stream().toList() == expectedResult

        where:
        tasksForScoring | subTasksForScoring    || expectedResult
        TASKS_OPTIONS[0]| SUBTASKS_OPTIONS[0]   || EXPECTED_OPTIONS[0]
        TASKS_OPTIONS[1]| SUBTASKS_OPTIONS[1]   || EXPECTED_OPTIONS[1]
        TASKS_OPTIONS[2]| SUBTASKS_OPTIONS[2]   || EXPECTED_OPTIONS[2]
    }

    @Shared
    def taskForScoringProj1 = Mock(TaskForScoringProj)

    @Shared
    def taskForScoringProj2 = Mock(TaskForScoringProj)

    @Shared
    def taskForScoringProj3 = Mock(TaskForScoringProj)

    @Shared
    def TASKS_OPTIONS = [
            [],
            [taskForScoringProj1],
            [taskForScoringProj2, taskForScoringProj3]
    ]

    def initializeTaskMocks() {
        taskForScoringProj1.getId() >> "task-id1"
        taskForScoringProj1.getFinishDate() >> LocalDateTime.now().plusDays(8)
        taskForScoringProj1.getPriority() >> Priority.TRIVIAL

        taskForScoringProj2.getId() >> "task-id2"
        taskForScoringProj2.getFinishDate() >> LocalDateTime.now().plusDays(6)
        taskForScoringProj2.getPriority() >> Priority.LOW

        taskForScoringProj3.getId() >> "task-id3"
        taskForScoringProj3.getFinishDate() >> LocalDateTime.now().plusDays(3)
        taskForScoringProj3.getPriority() >> Priority.MEDIUM
    }

    @Shared
    def subTaskForScoringProj1 = Mock(SubTaskForScoringProj)

    @Shared
    def subTaskForScoringProj2 = Mock(SubTaskForScoringProj)

    @Shared
    def subTaskForScoringProj3 = Mock(SubTaskForScoringProj)

    @Shared
    def SUBTASKS_OPTIONS = [
            [],
            [subTaskForScoringProj1, subTaskForScoringProj2],
            [subTaskForScoringProj3]
    ]

    def initializeSubTaskMocks() {
        subTaskForScoringProj1.getId() >> "subtask-id1"
        subTaskForScoringProj1.getFinishDate() >> LocalDateTime.now()
        subTaskForScoringProj1.getPriority() >> Priority.HIGH

        subTaskForScoringProj2.getId() >> "subtask-id2"
        subTaskForScoringProj2.getFinishDate() >> LocalDateTime.now().plusDays(6)
        subTaskForScoringProj2.getPriority() >> Priority.MEDIUM

        subTaskForScoringProj3.getId() >> "subtask-id3"
        subTaskForScoringProj3.getFinishDate() >> LocalDateTime.now().plusDays(2)
        subTaskForScoringProj3.getPriority() >> Priority.TRIVIAL
    }

    @Shared
    def expectedTaskScore1 = new TaskScore(200, "subtask-id1", TaskType.SUBTASK)

    @Shared
    def expectedTaskScore2 = new TaskScore(70, "subtask-id2", TaskType.SUBTASK)

    @Shared
    def expectedTaskScore3 = new TaskScore(10, "task-id1", TaskType.TASK)

    @Shared
    def expectedTaskScore4 = new TaskScore(100, "task-id3", TaskType.TASK)

    @Shared
    def expectedTaskScore5 = new TaskScore(60, "subtask-id3", TaskType.SUBTASK)

    @Shared
    def expectedTaskScore6 = new TaskScore(50, "task-id2", TaskType.TASK)

    @Shared
    def EXPECTED_OPTIONS = [
            [],
            [expectedTaskScore1, expectedTaskScore3, expectedTaskScore2],
            [expectedTaskScore4, expectedTaskScore6, expectedTaskScore5]
    ]

}
