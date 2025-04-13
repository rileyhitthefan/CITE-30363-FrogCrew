package edu.tcu.cs.frogcrewbackend.game;

import edu.tcu.cs.frogcrewbackend.game.converter.ScheduleDTOToScheduleConverter;
import edu.tcu.cs.frogcrewbackend.game.converter.ScheduleToScheduleDTOConverter;
import edu.tcu.cs.frogcrewbackend.game.dto.GameDTO;
import edu.tcu.cs.frogcrewbackend.game.dto.GameScheduleDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/gameSchedule")
public class GameScheduleController {
    private final GameScheduleService gameScheduleService;
    private final ScheduleDTOToScheduleConverter scheduleDTOToScheduleConverter;
    private final ScheduleToScheduleDTOConverter scheduleToScheduleDTOConverter;

    public GameScheduleController(GameScheduleService gameScheduleService,
                                  ScheduleDTOToScheduleConverter scheduleDTOToScheduleConverter,
                                  ScheduleToScheduleDTOConverter scheduleToScheduleDTOConverter) {
        this.gameScheduleService = gameScheduleService;
        this.scheduleDTOToScheduleConverter = scheduleDTOToScheduleConverter;
        this.scheduleToScheduleDTOConverter = scheduleToScheduleDTOConverter;
    }

    @PostMapping
    public Result addGameSchedule(@RequestBody @Valid GameSchedule newSchedule) {
        GameSchedule savedSchedule = this.gameScheduleService.addGameSchedule(newSchedule);
        GameScheduleDTO scheduleDTO = this.scheduleToScheduleDTOConverter.convert(savedSchedule);
        return new Result(true, StatusCode.SUCCESS, "Schedule added", scheduleDTO);
    }

    @PostMapping("/{scheduleId}/game")
    public Result addGameToSchedule(@PathVariable Integer scheduleId, @RequestBody @Valid GameDTO gameDTO) {
        Game newGame = new Game();
        newGame.setGameId(gameDTO.gameId());
        newGame.setScheduleId(scheduleId);
        newGame.setGameDate(gameDTO.gameDate());
        newGame.setVenue(gameDTO.venue());
        newGame.setOpponent(gameDTO.opponent());
        newGame.setFinalized(gameDTO.isFinalized());

        Game scheduledGame = this.gameScheduleService.addGameToSchedule(scheduleId, newGame);

        GameDTO savedDTO = new GameDTO(
                scheduledGame.getGameId(),
                scheduledGame.getScheduleId(),
                scheduledGame.getGameDate(),
                scheduledGame.getVenue(),
                scheduledGame.getOpponent(),
                scheduledGame.getFinalized()
        );
        return new Result(true, StatusCode.SUCCESS, "Game added to schedule", savedDTO);
    }
}
