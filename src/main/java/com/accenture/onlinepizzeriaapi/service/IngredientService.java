package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
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

    IngredientResponseDto findByName(String name);

    /**
     * @param requestDto
     * @return IngredientResponseDto
     */
    IngredientResponseDto addIngredient(IngredientRequestDto requestDto);

    IngredientResponseDto patchIngredient(String name, IngredientPatchDto patchDto);

    void checkIngredient(IngredientRequestDto requestDto);
}
