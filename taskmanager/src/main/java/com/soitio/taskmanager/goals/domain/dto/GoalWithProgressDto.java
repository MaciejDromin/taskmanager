package com.soitio.taskmanager.goals.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class GoalWithProgressDto {

    String id;
    String name;
    LocalDateTime creationDate;
    LocalDateTime finishDate;
    int progress;

}
