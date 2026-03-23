package com.accenture.onlinepizzeriaapi.service.dto;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.enums.Size;

import java.util.List;
import java.util.UUID;

public record PizzaResponseDto(

        UUID id,
        String name,
        Size size,
        Double price,
        List<Ingredient> ingredients,
        Boolean removedFromMenu
) {
}
