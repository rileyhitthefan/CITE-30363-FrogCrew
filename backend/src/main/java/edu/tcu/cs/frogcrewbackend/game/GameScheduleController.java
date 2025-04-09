package edu.tcu.cs.frogcrewbackend.schedule;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/gameSchedule")
public class GameScheduleController {
    private final GameScheduleService gameScheduleService;

    public GameScheduleController(GameScheduleService gameScheduleService) {
        this.gameScheduleService = gameScheduleService;
    }
}
