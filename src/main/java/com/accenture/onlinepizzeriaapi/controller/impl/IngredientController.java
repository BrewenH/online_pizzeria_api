package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.IngredientApi;
import com.accenture.onlinepizzeriaapi.service.IngredientService;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
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

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<List<IngredientResponseDto>> findAll() {

        log.info("Access to endpoint GET/ingredients");
        return ResponseEntity.ok(ingredientService.findAll());
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<IngredientResponseDto> findById(UUID idIngredient) {
        log.info("Access to endpoint GET/ingredients/id/{id}");
        return ResponseEntity.ok(ingredientService.findById(idIngredient));
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<IngredientResponseDto> findByName(String name) {
        log.info("Access to endpoint GET/ingredients/{name}");
        return ResponseEntity.ok(ingredientService.findByName(name));
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<IngredientResponseDto> patchIngredient(String ingredientName, IngredientPatchDto patchDto) {
        log.info("Access to endpoint PATCH/ingredients/{name}");
        IngredientResponseDto responseDto = ingredientService.patchIngredient(ingredientName,patchDto);
        return ResponseEntity.ok(responseDto);
    }
}
