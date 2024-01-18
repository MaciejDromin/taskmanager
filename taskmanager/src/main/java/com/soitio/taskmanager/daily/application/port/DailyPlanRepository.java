package com.soitio.taskmanager.daily.application.port;

import com.soitio.taskmanager.daily.domain.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlan, String> {

    Optional<DailyPlan> findByEffectiveDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
