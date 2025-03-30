package edu.tcu.cs.frogcrewbackend.member.dto;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(
        Integer id,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        String email,
        String phoneNumber,
        String role,
        String positions){}
