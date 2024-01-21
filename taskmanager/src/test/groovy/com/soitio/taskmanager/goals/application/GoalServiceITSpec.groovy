package com.soitio.taskmanager.goals.application

import com.soitio.taskmanager.BasePostgresITSpec
import com.soitio.taskmanager.goals.application.port.GoalRepository
import com.soitio.taskmanager.goals.domain.dto.GoalDto
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.ZoneOffset

class GoalServiceITSpec extends BasePostgresITSpec {

    @Subject
    @Autowired
    GoalService goalService

    @Autowired
    GoalRepository goalRepository

    def "should get empty goal list" () {
        expect:
        goalService.findAllGoals().size() == 0
    }

    @Transactional
    def "should properly create goal" () {
        given:
        def goal = GoalDto.builder()
                .name("Test Goal")
                .finishDate(LocalDateTime.now(ZoneOffset.UTC))
                .build()

        when:
        def createdGoal = goalService.createGoal(goal)

        then:
        with(goalRepository.getReferenceById(createdGoal.getId())) {
            creationDate == createdGoal.creationDate
            finishDate == createdGoal.finishDate
            name == createdGoal.name
        }

        cleanup:
        goalRepository.deleteById(createdGoal.getId())
    }

}
