package com.soitio.taskmanager.goals.application.port;

import com.soitio.taskmanager.goals.domain.Goal;
import com.soitio.taskmanager.goals.domain.GoalProgressProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, String> {

    List<GoalProgressProjection> findAllProjBy();

    Optional<Goal> findGoalByTasksIdIn(List<String> taskId);

}
