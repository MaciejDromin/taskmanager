package com.soitio.taskmanager.tasks.domain.proj;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;
import java.util.List;

public interface SimpleTaskProj {

    String getId();

    String getName();

    Priority getPriority();

    Status getStatus();

    List<SimpleSubTaskProj> getSubTasks();

}
