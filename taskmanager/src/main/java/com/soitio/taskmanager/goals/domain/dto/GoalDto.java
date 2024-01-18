package com.soitio.taskmanager.goals.domain.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class GoalDto {

    private String id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime finishDate;

}
