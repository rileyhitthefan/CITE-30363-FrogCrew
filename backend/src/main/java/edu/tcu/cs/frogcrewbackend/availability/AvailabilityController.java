package edu.tcu.cs.frogcrewbackend.availability;

import edu.tcu.cs.frogcrewbackend.availability.converter.AvailabilityDTOToAvailabilityConverter;
import edu.tcu.cs.frogcrewbackend.availability.converter.AvailabilityToAvailabilityDTOConverter;
import edu.tcu.cs.frogcrewbackend.availability.dto.AvailabilityDTO;
import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.game.GameScheduleService;
import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.member.UserService;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/availability")
public class AvailabilityController {
    private final AvailabilityService availService;
    private final UserService userService;
    private final GameScheduleService gameScheduleService;
    private final AvailabilityToAvailabilityDTOConverter availabilityToAvailabilityDTOConverter;
    private final AvailabilityDTOToAvailabilityConverter availabilityDTOToAvailabilityConverter;

    public AvailabilityController(AvailabilityService availService, UserService userService, GameScheduleService gameScheduleService, AvailabilityToAvailabilityDTOConverter availabilityToAvailabilityDTOConverter, AvailabilityDTOToAvailabilityConverter availabilityDTOToAvailabilityConverter) {
        this.availService = availService;
        this.userService = userService;
        this.gameScheduleService = gameScheduleService;
        this.availabilityToAvailabilityDTOConverter = availabilityToAvailabilityDTOConverter;
        this.availabilityDTOToAvailabilityConverter = availabilityDTOToAvailabilityConverter;
    }

    @PostMapping
    public Result addAvailability(@RequestBody @Valid AvailabilityDTO availabilityDTO) {
        Member availableUser = this.userService.findMemberById(availabilityDTO.userId());
        Game availableGame = this.gameScheduleService.findGameById(availabilityDTO.gameId());
        Availability availability = availabilityDTOToAvailabilityConverter.convert(availabilityDTO);

        availability.setGame(availableGame);
        availability.setUser(availableUser);
        Availability savedAvailability = this.availService.addAvailability(availability);

        AvailabilityDTO savedAvailabilityDTO = this.availabilityToAvailabilityDTOConverter.convert(savedAvailability);
        return new Result(true, StatusCode.SUCCESS, "New availability added", savedAvailabilityDTO);
    }
}

