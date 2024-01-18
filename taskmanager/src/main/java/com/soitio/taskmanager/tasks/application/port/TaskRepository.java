package com.soitio.taskmanager.tasks.application.port;

import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<TaskForScoringProj> findAllForScoringByStatusNot(Status status);

    List<SimpleTaskProj> findAllProjByIdIn(List<String> taskIds);

    List<SimpleTaskProj> findAllBySubTasksIdIn(List<String> subTaskIds);

}
