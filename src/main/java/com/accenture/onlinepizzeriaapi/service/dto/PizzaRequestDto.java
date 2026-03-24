package com.accenture.onlinepizzeriaapi.service.dto;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.enums.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PizzaRequestDto(
        @NotBlank(message = "pizza.name.null")
        String name,
        @NotNull(message = "pizza.size.null")
        Size size,
        @NotNull(message = "pizza.price.null")
        Double price,
        @NotNull
        List<Ingredient> ingredients,
        @NotNull
        Boolean removedFromMenu

) {
}
