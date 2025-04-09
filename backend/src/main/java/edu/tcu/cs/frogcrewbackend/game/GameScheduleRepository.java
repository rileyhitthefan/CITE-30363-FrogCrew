package edu.tcu.cs.frogcrewbackend.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameScheduleRepository extends JpaRepository<GameSchedule, Integer> {
}
