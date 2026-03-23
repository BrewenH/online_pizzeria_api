package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.IngredientApi;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class IngredientController implements IngredientApi {
    @Override
    public ResponseEntity<List<IngredientResponseDto>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<IngredientResponseDto> findById(UUID idIngredient) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addIngredient(IngredientRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteIngredient(UUID idIngredient) {
        return null;
    }

    @Override
    public ResponseEntity<IngredientResponseDto> patchIngredient(UUID idIngredient, IngredientRequestDto requestDto) {
        return null;
    }
}
