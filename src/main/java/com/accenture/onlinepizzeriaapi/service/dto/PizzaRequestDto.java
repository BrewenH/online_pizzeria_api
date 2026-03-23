package com.accenture.onlinepizzeriaapi.service.dto;

import com.accenture.onlinepizzeriaapi.model.Size;
import jakarta.validation.constraints.NotBlank;

public record PizzaRequestDto(
        @NotBlank(message = "pizza.name.null")
        String name,
        Size size,
        @NotBlank(message = "pizza.price.null")
        Double price,
        List<Ingredient> ingredients,
        Boolean removedFromMenu

) {
}
