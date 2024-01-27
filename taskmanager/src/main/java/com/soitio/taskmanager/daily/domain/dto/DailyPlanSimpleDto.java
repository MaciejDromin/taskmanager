package com.soitio.taskmanager.daily.domain.dto;

import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUiDto;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DailyPlanSimpleDto {

    String id;
    LocalDateTime effectiveDate;
    Set<SimpleTaskUiDto> tasks;

}
