package edu.tcu.cs.frogcrewbackend.game;

import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
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

    public Game addGameToSchedule(Integer scheduleId, Game newGame) {
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ObjectNotFoundException("GameSchedule", scheduleId));
        newGame.setSchedule(schedule);
        schedule.getGames().add(newGame);
        gameScheduleRepository.save(schedule);
        return newGame;
    }

}
