package com.soitio.taskmanager.tasks.domain.proj;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailedTaskProj {

    String getId();
    String getName();
    String getDescription();
    Priority getPriority();
    Status getStatus();
    LocalDateTime getFinishDate();
    List<SimpleSubTaskProj> getSubTasks();

}
