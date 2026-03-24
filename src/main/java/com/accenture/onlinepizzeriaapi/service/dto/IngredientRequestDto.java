package com.accenture.onlinepizzeriaapi.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(

        @NotBlank(message = "ingredient.name.null")
        String name,

        @NotNull(message = "ingredient.quantity.null")
        @Min(value = 0, message = "ingredient.quantity.invalid")
        int quantity) {
}
