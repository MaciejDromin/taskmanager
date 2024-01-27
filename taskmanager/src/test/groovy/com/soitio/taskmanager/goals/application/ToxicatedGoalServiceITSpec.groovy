package com.soitio.taskmanager.goals.application

import com.soitio.taskmanager.ProxiedPostgresITSpec
import com.soitio.taskmanager.goals.domain.dto.GoalDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import java.time.LocalDateTime
import java.time.ZoneOffset

class ToxicatedGoalServiceITSpec extends ProxiedPostgresITSpec {

    @Subject
    @Autowired
    GoalService goalService

    // flaky test, fix
//    def "should properly create goal" () {
//        given:
//        proxy.disable()
//
//        def goal = GoalDto.builder()
//                .name("Test Goal")
//                .finishDate(LocalDateTime.now(ZoneOffset.UTC))
//                .build()
//
//        when:
//        goalService.createGoal(goal)
//
//        then:
//        def e = thrown(Exception.class)
//        e.getMessage() == "Could not open JPA EntityManager for transaction"
//
//        cleanup:
//        proxy.enable()
//    }

}
