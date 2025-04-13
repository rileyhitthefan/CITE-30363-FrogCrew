package edu.tcu.cs.frogcrewbackend.game.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GameDTO (
        @NotNull(message = "Game id is required")
        Integer gameId,

        @NotNull(message = "Schedule id is required")
        Integer scheduleId,

        @NotEmpty(message = "Game date is required")
        String gameDate,

        @NotEmpty(message = "Venue is required")
        String venue,

        @NotEmpty(message = "Opponent is required")
        String opponent,

        @NotNull(message = "Venue is required")
        Boolean isFinalized
) {}
