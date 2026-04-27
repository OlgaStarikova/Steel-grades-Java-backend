package com.example.webksz.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateGroupRequestDto(
        @NotBlank
        String name
) {
}