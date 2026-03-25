package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;

import java.util.List;
import java.util.UUID;

public interface IngredientService {

    /**
     * @param
     * @return List<IngredientResponseDto>
     */
    List<IngredientResponseDto> findAll();

    IngredientResponseDto findById(UUID id);

    /**
     * @param requestDto
     * @return IngredientResponseDto
     */
    IngredientResponseDto addIngredient(IngredientRequestDto requestDto);

    void deleteIngredient(UUID idIngredient);

    IngredientResponseDto patchIngredient(UUID idIngredient, IngredientRequestDto requestDto);

    void checkIngredient(IngredientRequestDto requestDto);
}
