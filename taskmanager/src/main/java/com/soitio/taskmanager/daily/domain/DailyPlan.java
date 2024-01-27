package com.soitio.taskmanager.daily.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(columnList = "effectiveDate")
})
public class DailyPlan {

    @Id
    @UuidGenerator
    private String id;

    private LocalDateTime effectiveDate;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "daily_task_ids")
    private List<String> dailyTaskIds = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "daily_sub_task_ids")
    private List<String> dailySubTaskIds = new ArrayList<>();

}
