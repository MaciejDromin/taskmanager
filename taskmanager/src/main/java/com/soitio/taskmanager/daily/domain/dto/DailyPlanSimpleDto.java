package com.soitio.taskmanager.daily.domain.dto;

import com.soitio.taskmanager.tasks.domain.dto.SimpleTaskUIDto;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class DailyPlanSimpleDto {

    String id;
    LocalDateTime effectiveDate;
    Set<SimpleTaskUIDto> tasks;

}
