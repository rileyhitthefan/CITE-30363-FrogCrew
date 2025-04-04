package edu.tcu.cs.frogcrewbackend.availability;

import edu.tcu.cs.frogcrewbackend.availability.converter.AvailabilityDTOToAvailabilityConverter;
import edu.tcu.cs.frogcrewbackend.availability.converter.AvailabilityToAvailabilityDTOConverter;
import edu.tcu.cs.frogcrewbackend.availability.dto.AvailabilityDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/availability")
public class AvailabilityController {
    private final AvailabilityService availService;
    private final AvailabilityToAvailabilityDTOConverter availabilityToAvailabilityDTOConverter;
    private final AvailabilityDTOToAvailabilityConverter availabilityDTOToAvailabilityConverter;

    public AvailabilityController(AvailabilityService availService, AvailabilityToAvailabilityDTOConverter availabilityToAvailabilityDTOConverter, AvailabilityDTOToAvailabilityConverter availabilityDTOToAvailabilityConverter) {
        this.availService = availService;
        this.availabilityToAvailabilityDTOConverter = availabilityToAvailabilityDTOConverter;
        this.availabilityDTOToAvailabilityConverter = availabilityDTOToAvailabilityConverter;
    }

    @PostMapping
    public Result addAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        return null;
    }
}
