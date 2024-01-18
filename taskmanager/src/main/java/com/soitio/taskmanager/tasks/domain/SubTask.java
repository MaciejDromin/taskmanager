package com.soitio.taskmanager.tasks.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    private String description;

    private Priority priority;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime finishDate;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubTask subTask = (SubTask) o;
        return id != null && Objects.equals(id, subTask.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
