package edu.tcu.cs.frogcrewbackend.game.dto;

import jakarta.validation.constraints.NotEmpty;

public record GameScheduleDTO (
        @NotEmpty(message = "Schedule is required")
        Integer id,

        @NotEmpty(message = "Sport is required")
        String sport,

        @NotEmpty(message = "Season is required")
        String season
) { }
