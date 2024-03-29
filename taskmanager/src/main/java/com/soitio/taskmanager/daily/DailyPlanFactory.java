package com.soitio.taskmanager.daily;

import com.soitio.taskmanager.daily.domain.DailyPlan;
import com.soitio.taskmanager.daily.domain.dto.DailyPlanSimpleDto;
import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUiDto;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DailyPlanFactory {

    public DailyPlanSimpleDto from(DailyPlan dailyPlan, Set<SimpleTaskUiDto> simpleTaskUis) {
        return DailyPlanSimpleDto.builder()
                .id(dailyPlan.getId())
                .effectiveDate(dailyPlan.getEffectiveDate())
                .tasks(simpleTaskUis)
                .build();
    }

}
