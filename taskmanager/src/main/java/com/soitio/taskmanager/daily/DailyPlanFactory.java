package com.soitio.taskmanager.daily;

import com.soitio.taskmanager.daily.domain.DailyPlan;
import com.soitio.taskmanager.daily.domain.dto.DailyPlanSimpleDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DailyPlanFactory {

    public DailyPlanSimpleDto from(DailyPlan dailyPlan, Set<SimpleTaskUIDto> simpleTaskUIs) {
        return DailyPlanSimpleDto.builder()
                .id(dailyPlan.getId())
                .effectiveDate(dailyPlan.getEffectiveDate())
                .tasks(simpleTaskUIs)
                .build();
    }

}
