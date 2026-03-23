package com.accenture.onlinepizzeriaapi.mapper;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient toIngredient(IngredientRequestDto ingredientRequestDto);

    IngredientResponseDto toIngredientResponseDto(Ingredient ingredient);
}
