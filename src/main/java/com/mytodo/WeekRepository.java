package com.mytodo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WeekRepository extends JpaRepository<Week, Long> {
    Optional<Week> findByYearAndWeekNumber(int year, int weekNumber);
}
