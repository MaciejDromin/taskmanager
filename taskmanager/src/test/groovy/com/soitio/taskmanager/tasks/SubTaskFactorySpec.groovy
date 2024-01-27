package com.soitio.taskmanager.tasks

import com.soitio.taskmanager.tasks.domain.Priority
import com.soitio.taskmanager.tasks.domain.Status
import com.soitio.taskmanager.tasks.domain.SubTask
import com.soitio.taskmanager.tasks.domain.Task
import com.soitio.taskmanager.tasks.domain.dto.SubTaskCreationDto
import com.soitio.taskmanager.tasks.domain.dto.SubTaskDto
import spock.lang.Specification
import spock.lang.Subject
import java.time.LocalDateTime

class SubTaskFactorySpec extends Specification {

    @Subject
    SubTaskFactory subTaskFactory = new SubTaskFactory()

    def "should properly create SubTaskDto"() {
        given:
        def subTask = SubTask.builder()
                .id("test-id")
                .name("name")
                .description("description")
                .priority(Priority.HIGH)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now())
                .status(Status.FINISHED)
                .build()

        when:
        def result = subTaskFactory.from(subTask)

        then:
        with(result) {
            id == subTask.getId()
            name == subTask.getName()
            description == subTask.getDescription()
            priority == subTask.getPriority()
            createdDate == subTask.getCreatedDate()
            updatedDate == subTask.getUpdatedDate()
            finishDate == subTask.getFinishDate()
            status == subTask.getStatus()
        }
    }

    def "should properly create SubTask"() {
        given:
        def subTask = SubTaskCreationDto.builder()
                .name("name")
                .description("description")
                .priority(Priority.HIGH)
                .finishDate(LocalDateTime.now())
                .build()
        def task = Task.builder()
                .id("task-id")
                .build()

        when:
        def result = subTaskFactory.createSubTask(subTask, task)

        then:
        with(result) {
            name == subTask.getName()
            description == subTask.getDescription()
            priority == subTask.getPriority()
            createdDate != null
            updatedDate != null
            finishDate == subTask.getFinishDate()
            status == Status.NOT_STARTED
            it.task == task
        }
    }

    def "should properly update SubTask"() {
        given:
        def subTask = SubTask.builder()
                .id("test-id")
                .name("name")
                .description("description")
                .priority(Priority.HIGH)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now())
                .status(Status.FINISHED)
                .build()
        def subTaskUpdate = SubTaskDto.builder()
                .name("update-name")
                .description("update-description")
                .priority(Priority.LOW)
                .finishDate(LocalDateTime.now())
                .build()

        when:
        subTaskFactory.updateSubTask(subTask, subTaskUpdate)

        then:
        with(subTask) {
            name == subTaskUpdate.getName()
            description == subTaskUpdate.getDescription()
            priority == subTaskUpdate.getPriority()
            finishDate == subTaskUpdate.getFinishDate()
        }
    }

}
