package edu.tcu.cs.frogcrewbackend.crew.dto;

public record CrewedUserDTO(
        Integer userId,
        Integer gameId,
        String fullName,
        String position,
        String reportTime
) {}
