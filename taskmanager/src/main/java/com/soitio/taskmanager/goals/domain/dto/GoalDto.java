package com.soitio.taskmanager.goals.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoalDto {

    private String id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime finishDate;

}
