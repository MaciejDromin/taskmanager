package com.soitio.taskmanager.goals.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GoalWithProgressDto {

    String id;
    String name;
    LocalDateTime creationDate;
    LocalDateTime finishDate;
    int progress;

}
