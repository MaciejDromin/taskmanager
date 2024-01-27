package com.soitio.taskmanager.goals

import com.soitio.taskmanager.goals.domain.Goal
import com.soitio.taskmanager.goals.domain.GoalProgressProjection
import com.soitio.taskmanager.goals.domain.dto.GoalDto
import com.soitio.taskmanager.tasks.domain.Status
import spock.lang.Specification
import spock.lang.Subject
import java.time.LocalDateTime
import java.time.Month

class GoalFactorySpec extends Specification {

    @Subject
    GoalFactory goalFactory = new GoalFactory()

    def "should properly map to GoalDto"() {
        given:
        def goal = Goal.builder()
                .id("goal-id")
                .name("goal-name")
                .creationDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now())
                .build()

        when:
        def result = goalFactory.from(goal)

        then:
        with(result) {
            id == goal.getId()
            name == goal.getName()
            creationDate == goal.getCreationDate()
            finishDate == goal.getFinishDate()
        }
    }

    def "should properly create goal"() {
        given:
        def goal = GoalDto.builder()
                .name("goal-name")
                .finishDate(LocalDateTime.now())
                .build()

        when:
        def result = goalFactory.createGoal(goal)

        then:
        with(result) {
            name == goal.getName()
            finishDate == goal.getFinishDate()
        }
    }

    def "should properly update goal"() {
        given:
        def goal = Goal.builder()
                .id("goal-id")
                .name("goal-name")
                .creationDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now())
                .build()
        def goalUpdate = GoalDto.builder()
                .id("goal-id")
                .name("goal-name-update")
                .creationDate(LocalDateTime.now())
                .finishDate(LocalDateTime.of(2024, Month.AUGUST, 12, 10, 10))
                .build()

        when:
        goalFactory.updateGoal(goal, goalUpdate)

        then:
        with(goal) {
            id == goal.getId()
            creationDate == goal.getCreationDate()
            name == goalUpdate.getName()
            finishDate == goalUpdate.getFinishDate()
        }
    }

    def "should properly create goal with progress"() {
        given:
        def proj = Mock(GoalProgressProjection)

        proj.getId() >> "test-id"
        proj.getName() >> "test-name"
        proj.getCreationDate() >> LocalDateTime.now()
        proj.getFinishDate() >> LocalDateTime.now()

        and: "mock tasks"
        def task1 = Mock(GoalProgressProjection.TaskForProgressProj)
        def task2 = Mock(GoalProgressProjection.TaskForProgressProj)
        def task3 = Mock(GoalProgressProjection.TaskForProgressProj)

        task1.getStatus() >> Status.FINISHED
        task2.getStatus() >> Status.ON_HOLD
        task3.getStatus() >> Status.IN_PROGRESS

        proj.getTasks() >> [
                task1, task2, task3
        ]

        when:
        def result = goalFactory.createGoalWithProgress(proj)

        then:
        with(result) {
            id == proj.getId()
            name == proj.getName()
            creationDate == proj.getCreationDate()
            finishDate == proj.getFinishDate()
            progress == goalFactory.calculateProgress(proj.getTasks())
        }
    }

    def "should return 0 progress if no task exist in list"() {
        expect:
        goalFactory.calculateProgress([]) == 0
    }

}
