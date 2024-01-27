package com.soitio.taskmanager.tasks.application.port;

import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.Task;
import com.soitio.taskmanager.tasks.domain.proj.SimpleTaskProj;
import com.soitio.taskmanager.tasks.domain.proj.TaskForScoringProj;
import com.soitio.taskmanager.tasks.domain.proj.TaskWithStatusProj;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<TaskForScoringProj> findAllForScoringByStatusNot(Status status);

    <T> List<T> findAllProjByIdIn(List<String> taskIds, Class<T> clazz);

    List<SimpleTaskProj> findAllBySubTasksIdIn(List<String> subTaskIds);

    TaskWithStatusProj getProjById(String taskId);

}
