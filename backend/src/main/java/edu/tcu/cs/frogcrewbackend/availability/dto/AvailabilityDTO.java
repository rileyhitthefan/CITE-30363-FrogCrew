package edu.tcu.cs.frogcrewbackend.availability.dto;

import jakarta.validation.constraints.NotEmpty;

public record AvailabilityDTO(
        @NotEmpty(message = "User id is required")
        Integer userId,

        @NotEmpty(message = "Game id is required")
        Integer gameId,

        @NotEmpty(message = "Availability is required")
        Boolean availability,

        String comment
) {}
