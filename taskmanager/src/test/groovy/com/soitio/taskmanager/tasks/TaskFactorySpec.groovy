package com.soitio.taskmanager.tasks

import com.soitio.taskmanager.goals.domain.Goal
import com.soitio.taskmanager.tasks.domain.Priority
import com.soitio.taskmanager.tasks.domain.Status
import com.soitio.taskmanager.tasks.domain.Task
import com.soitio.taskmanager.tasks.domain.dto.TaskCreationDto
import com.soitio.taskmanager.tasks.domain.dto.TaskUpdateDto
import com.soitio.taskmanager.tasks.domain.proj.DetailedTaskProj
import com.soitio.taskmanager.tasks.domain.proj.SimpleSubTaskProj
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj
import spock.lang.Specification
import spock.lang.Subject
import java.time.LocalDateTime

class TaskFactorySpec extends Specification {

    @Subject
    TaskFactory taskFactory = new TaskFactory()

    def "should properly create TaskDto"() {
        given:
        def task = Task.builder()
                .id("task-id")
                .name("name")
                .description("desc")
                .priority(Priority.HIGH)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now())
                .status(Status.FINISHED)
                .build()

        when:
        def result = taskFactory.from(task)

        then:
        with(result) {
            id == task.getId()
            name == task.getName()
            description == task.getDescription()
            priority == task.getPriority()
            createdDate == task.getCreatedDate()
            updatedDate == task.getUpdatedDate()
            finishDate == task.getFinishDate()
            status == task.getStatus()
        }
    }

    def "should properly create task"() {
        given:
        def task = TaskCreationDto.builder()
                .name("name")
                .description("desc")
                .priority(Priority.HIGH)
                .finishDate(LocalDateTime.now())
                .build()
        def goal = Goal.builder()
                .id("goal-id")
                .build()

        when:
        def result = taskFactory.createTask(task, goal)

        then:
        with(result) {
            name == task.getName()
            description == task.getDescription()
            priority == task.getPriority()
            createdDate != null
            updatedDate != null
            finishDate == task.getFinishDate()
            status == Status.NOT_STARTED
            it.goal == goal
        }
    }

    def "should properly create detailedTask"() {
        given:
        def proj = Mock(DetailedTaskProj)

        proj.getId() >> "task-id"
        proj.getName() >> "task-name"
        proj.getDescription() >> "task-desc"
        proj.getStatus() >> Status.FINISHED
        proj.getPriority() >> Priority.LOW
        proj.getFinishDate() >> LocalDateTime.now()

        and: "subtasks"
        def subTask1 = Mock(SimpleSubTaskProj)
        def subTask2 = Mock(SimpleSubTaskProj)

        subTask1.getId() >> "sub-id-1"
        subTask1.getName() >> "sub-name-1"
        subTask1.getPriority() >> Priority.LOW
        subTask1.getStatus() >> Status.IN_PROGRESS

        subTask2.getId() >> "sub-id-2"
        subTask2.getName() >> "sub-name-2"
        subTask2.getPriority() >> Priority.MEDIUM
        subTask2.getStatus() >> Status.ON_HOLD

        proj.getSubTasks() >> [
                subTask1, subTask2
        ]

        when:
        def result = taskFactory.detailedTask(proj)

        then:
        with(result) {
            id == proj.getId()
            name == proj.getName()
            description == proj.getDescription()
            priority == proj.getPriority()
            finishDate == proj.getFinishDate()
            status == proj.getStatus()
            with(subTasks.first()) {
                id == subTask1.getId()
                name == subTask1.getName()
                priority == subTask1.getPriority()
                status == subTask1.getStatus()
            }
            with(subTasks.last()) {
                id == subTask2.getId()
                name == subTask2.getName()
                priority == subTask2.getPriority()
                status == subTask2.getStatus()
            }
        }
    }

    def "should properly create simple UI task"() {
        given:
        def proj = Mock(SimpleTaskProj)

        proj.getId() >> "task-id"
        proj.getName() >> "task-name"
        proj.getStatus() >> Status.FINISHED
        proj.getPriority() >> Priority.LOW

        and: "subtasks"
        def subTask1 = Mock(SimpleSubTaskProj)
        def subTask2 = Mock(SimpleSubTaskProj)

        subTask1.getId() >> "sub-id-1"
        subTask1.getName() >> "sub-name-1"
        subTask1.getPriority() >> Priority.LOW
        subTask1.getStatus() >> Status.IN_PROGRESS

        subTask2.getId() >> "sub-id-2"
        subTask2.getName() >> "sub-name-2"
        subTask2.getPriority() >> Priority.MEDIUM
        subTask2.getStatus() >> Status.ON_HOLD

        proj.getSubTasks() >> [
                subTask1, subTask2
        ]

        when:
        def result = taskFactory.simpleUiTask(proj)

        then:
        with(result) {
            id == proj.getId()
            name == proj.getName()
            priority == proj.getPriority()
            status == proj.getStatus()
            with(subTasks.first()) {
                id == subTask1.getId()
                name == subTask1.getName()
                priority == subTask1.getPriority()
                status == subTask1.getStatus()
            }
            with(subTasks.last()) {
                id == subTask2.getId()
                name == subTask2.getName()
                priority == subTask2.getPriority()
                status == subTask2.getStatus()
            }
        }
    }

    def "should properly updateTask"() {
        given:
        def task = Task.builder()
                .id("task-id")
                .name("task-name")
                .description("task-desc")
                .priority(Priority.LOW)
                .finishDate(LocalDateTime.now())
                .goal(Goal.builder()
                        .id("goal-id")
                        .build())
                .build()

        def taskUpdate = TaskUpdateDto.builder()
                .name("task-name-upd")
                .description("task-desc-upd")
                .priority(Priority.MEDIUM)
                .finishDate(LocalDateTime.now())
                .build()

        def goalUpdate = Goal.builder()
                .id("goal-id-update")
                .build()
        when:
        taskFactory.updateTask(task, taskUpdate, goalUpdate)

        then:
        with(task) {
            name == taskUpdate.getName()
            description == taskUpdate.getDescription()
            priority == taskUpdate.getPriority()
            finishDate == taskUpdate.getFinishDate()
            goal == goalUpdate
        }
    }

    def "should properly create simple SubTask"() {

    }

}
