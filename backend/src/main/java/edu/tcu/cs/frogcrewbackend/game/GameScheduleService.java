package edu.tcu.cs.frogcrewbackend.schedule;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameScheduleService {
    private final GameScheduleRepository gameScheduleRepository;

    public GameScheduleService(GameScheduleRepository gameRepository) {
        this.gameScheduleRepository = gameRepository;
    }

    public GameSchedule addGameSchedule(GameSchedule gameSchedule) {
        return this.gameScheduleRepository.save(gameSchedule);
    }
}
