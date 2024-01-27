package com.soitio.taskmanager.tasks.application.port;

import com.soitio.taskmanager.tasks.domain.Status;
import com.soitio.taskmanager.tasks.domain.SubTask;
import com.soitio.taskmanager.tasks.domain.proj.SubTaskForScoringProj;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, String> {

    List<SubTaskForScoringProj> findAllForScoringByStatusNot(Status status);

}
