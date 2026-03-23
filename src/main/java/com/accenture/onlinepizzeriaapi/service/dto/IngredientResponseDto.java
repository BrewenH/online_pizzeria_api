package com.accenture.onlinepizzeriaapi.service.dto;

import java.util.UUID;

public record IngredientResponseDto(UUID id, String name, int quantity) {
}
