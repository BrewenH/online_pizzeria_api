package com.accenture.onlinepizzeriaapi.service.dto;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.enums.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

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
