package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.IngredientApi;
import com.accenture.onlinepizzeriaapi.service.IngredientService;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class IngredientController implements IngredientApi {

    private final IngredientService ingredientService;

    @Override
    public ResponseEntity<List<IngredientResponseDto>> findAll() {

        return ResponseEntity.ok(ingredientService.findAll());
    }

    @Override
    public ResponseEntity<IngredientResponseDto> findById(UUID idIngredient) {

        return ResponseEntity.ok(ingredientService.findById());
    }

    @Override
    public ResponseEntity<Void> addIngredient(IngredientRequestDto requestDto) {
        log.info("Access to endpoint /POST/ingredients");
        IngredientResponseDto responseDto = ingredientService.addIngredient(requestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteIngredient(UUID idIngredient) {
        ingredientService.deleteIngredient(idIngredient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<IngredientResponseDto> patchIngredient(UUID idIngredient, IngredientRequestDto requestDto) {
        IngredientResponseDto responseDto = ingredientService.patchIngredient(idIngredient,requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
