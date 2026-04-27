package com.example.webksz.dto;

public record SteelGradeDto(
        Long id,
        String name,
        Long groupId,
        String groupName,
        Boolean hasRecipe
) {
}