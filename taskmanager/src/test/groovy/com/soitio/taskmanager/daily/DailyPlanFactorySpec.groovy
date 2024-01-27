package com.soitio.taskmanager.daily

import com.soitio.taskmanager.daily.domain.DailyPlan
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUiDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DailyPlanFactorySpec extends Specification {

    @Subject
    DailyPlanFactory dailyPlanFactory = new DailyPlanFactory()

    def "should properly create simple daily plan"() {
        given:
        def dailyPlan = DailyPlan.builder()
                .id("test-id")
                .effectiveDate(LocalDateTime.now())
                .build()
        def simpleTasks = [SimpleTaskUiDto.builder().build()] as Set

        when:
        def result = dailyPlanFactory.from(dailyPlan, simpleTasks)

        then:
        with(result) {
            id == dailyPlan.getId()
            effectiveDate == dailyPlan.getEffectiveDate()
            simpleTasks == simpleTasks
        }
    }

}
