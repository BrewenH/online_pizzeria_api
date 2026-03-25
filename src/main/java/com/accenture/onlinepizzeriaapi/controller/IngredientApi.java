package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.controller.advice.ErrorDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ingredients", description = "Ingredient Stock Management API")
@RequestMapping("/ingredients")
public interface IngredientApi {

    @Operation(summary = "Find all ingredients")
    @ApiResponse(responseCode = "200", description = "List all ingredients")
    @GetMapping
    ResponseEntity<List<IngredientResponseDto>> findAll();

    @Operation(summary = "Get an ingredient by its id")
    @ApiResponse(responseCode = "200", description = "Ingredient found")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/id/{id}")
    ResponseEntity<IngredientResponseDto> findById(@Parameter(description = "Ingredient id not found", required = true) @PathVariable("id") UUID idIngredient);

    @Operation(summary = "Get an ingredient by its name")
    @ApiResponse(responseCode = "200", description = "Ingredient found")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{name}")
    ResponseEntity<IngredientResponseDto> findByName(@Parameter(description = "Ingredient name not found", required = true) @PathVariable("name") String name);

    @Operation(summary = "Add a new ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addIngredient(@RequestBody IngredientRequestDto requestDto);

    @Operation(summary = "Modify an ingredient or its stock quantity (PATCH)")
    @ApiResponse(responseCode = "200", description = "Ingredient successfully updated")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{name}")
    ResponseEntity<IngredientResponseDto> patchIngredient(@Parameter(description = "Ingredient name not found", required = true) @PathVariable("name") String ingredientName, @RequestBody IngredientPatchDto patchDto);
}
