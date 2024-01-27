package com.soitio.taskmanager.daily.application.port;

import com.soitio.taskmanager.daily.domain.DailyPlan;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlan, String> {

    Optional<DailyPlan> findByEffectiveDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
