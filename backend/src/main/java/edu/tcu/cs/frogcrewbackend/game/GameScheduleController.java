package edu.tcu.cs.frogcrewbackend.game;

import edu.tcu.cs.frogcrewbackend.game.converter.GameDTOToGameConverter;
import edu.tcu.cs.frogcrewbackend.game.converter.GameToGameDTOConverter;
import edu.tcu.cs.frogcrewbackend.game.converter.ScheduleDTOToScheduleConverter;
import edu.tcu.cs.frogcrewbackend.game.converter.ScheduleToScheduleDTOConverter;
import edu.tcu.cs.frogcrewbackend.game.dto.GameDTO;
import edu.tcu.cs.frogcrewbackend.game.dto.GameScheduleDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/gameSchedule")
public class GameScheduleController {
    private final GameScheduleService gameScheduleService;
    private final ScheduleDTOToScheduleConverter scheduleDTOToScheduleConverter;
    private final ScheduleToScheduleDTOConverter scheduleToScheduleDTOConverter;
    private final GameToGameDTOConverter gameToGameDTOConverter;
    private final GameDTOToGameConverter gameDTOToGameConverter;

    public GameScheduleController(GameScheduleService gameScheduleService,
                                  ScheduleDTOToScheduleConverter scheduleDTOToScheduleConverter,
                                  ScheduleToScheduleDTOConverter scheduleToScheduleDTOConverter, GameToGameDTOConverter gameToGameDTOConverter, GameDTOToGameConverter gameDTOToGameConverter) {
        this.gameScheduleService = gameScheduleService;
        this.scheduleDTOToScheduleConverter = scheduleDTOToScheduleConverter;
        this.scheduleToScheduleDTOConverter = scheduleToScheduleDTOConverter;
        this.gameToGameDTOConverter = gameToGameDTOConverter;
        this.gameDTOToGameConverter = gameDTOToGameConverter;
    }

    @PostMapping
    public Result addGameSchedule(@RequestBody @Valid GameSchedule newSchedule) {
        GameSchedule savedSchedule = this.gameScheduleService.addGameSchedule(newSchedule);
        GameScheduleDTO scheduleDTO = this.scheduleToScheduleDTOConverter.convert(savedSchedule);
        return new Result(true, StatusCode.SUCCESS, "Schedule added", scheduleDTO);
    }

    @GetMapping
    public Result findAllGameSchedules() {
        List<GameSchedule> schedules = this.gameScheduleService.findAllGameSchedules();
        // Convert to DTO
        List<GameScheduleDTO> schedulesDTO = schedules.stream()
                .map(this.scheduleToScheduleDTOConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Found all schedules", schedulesDTO);
    }

    @PostMapping("/{scheduleId}/game")
    public Result addGameToSchedule(@PathVariable Integer scheduleId, @RequestBody @Valid Game game) {
        Game newGame = this.gameScheduleService.addGame(game);
        this.gameScheduleService.addGameToSchedule(scheduleId, newGame);
        GameDTO assignedGameDTO = this.gameToGameDTOConverter.convert(newGame);
        return new Result(true, StatusCode.SUCCESS, "Game added to schedule " + scheduleId, assignedGameDTO);
    }
}
