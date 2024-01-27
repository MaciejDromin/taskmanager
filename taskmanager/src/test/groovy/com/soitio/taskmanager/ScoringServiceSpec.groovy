package com.soitio.taskmanager

import com.soitio.taskmanager.scoring.ScoringService
import com.soitio.taskmanager.tasks.application.SubTaskService
import com.soitio.taskmanager.tasks.application.TaskService
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ScoringServiceSpec extends Specification {

    TaskService taskService = Mock()
    SubTaskService subTaskService = Mock()

    @Subject
    ScoringService scoringService = new ScoringService(taskService, subTaskService)

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

    def static TASKS_OPTIONS = [
            [],
            [],
            []
    ]

    def static SUBTASKS_OPTIONS = [
            [],
            [],
            []
    ]

    def static EXPECTED_OPTIONS = [
            [],
            [],
            []
    ]

}
