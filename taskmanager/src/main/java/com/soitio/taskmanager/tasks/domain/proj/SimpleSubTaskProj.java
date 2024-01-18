package com.soitio.taskmanager.tasks.domain.proj;

import com.soitio.taskmanager.tasks.domain.Priority;
import com.soitio.taskmanager.tasks.domain.Status;

public interface SimpleSubTaskProj {

    String getId();
    String getName();
    Priority getPriority();
    Status getStatus();

}
