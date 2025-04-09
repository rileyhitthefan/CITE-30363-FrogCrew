package edu.tcu.cs.frogcrewbackend.game.dto;

import jakarta.validation.constraints.NotEmpty;

public record GameDTO (
        @NotEmpty(message = "Game id is required")
        Integer gameId,

        @NotEmpty(message = "Schedule id is required")
        Integer scheduleId,

        @NotEmpty(message = "Game date is required")
        String gameDate,

        @NotEmpty(message = "Venue is required")
        String venue,

        @NotEmpty(message = "Opponent is required")
        String opponent,

        @NotEmpty(message = "Venue is required")
        Boolean isFinalized
) {}
