package com.soitio.taskmanager.goals.application.port;

import com.soitio.taskmanager.goals.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, String> {
}
