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

    /**
     * <p>FindAll method lists all ingredients in stock and shows their names and quantity</p>
     * @return A 200 HTTP code and a response entity with a list of Ingredient Dtos
     */
    @Operation(summary = "Find all ingredients")
    @ApiResponse(responseCode = "200", description = "List all ingredients")
    @GetMapping
    ResponseEntity<List<IngredientResponseDto>> findAll();

    /**
     * <p>FindById method shows of ingredient searched with its id</p>
     *
     * @param idIngredient The id of the ingredient
     * @return An 200 HTTP code and a response entity with an ingredient in a Dto or 404 code if not found
     */
    @Operation(summary = "Get an ingredient by its id")
    @ApiResponse(responseCode = "200", description = "Ingredient found")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/id/{id}")
    ResponseEntity<IngredientResponseDto> findById(@Parameter(description = "Ingredient id not found", required = true) @PathVariable("id") UUID idIngredient);

    /**
     * <p>FindById method shows of ingredient searched with its name</p>
     *
     * @param name The name of the ingredient
     * @return A 200 HTTP code and a response entity with an ingredient in a Dto or a 404 code if nt found
     */
    @Operation(summary = "Get an ingredient by its name")
    @ApiResponse(responseCode = "200", description = "Ingredient found")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{name}")
    ResponseEntity<IngredientResponseDto> findByName(@Parameter(description = "Ingredient name not found", required = true) @PathVariable("name") String name);

    /**
     * <p>AddIngredient  method handles the creation of a new ingredient in stock</p>
     *
     * @param requestDto The information to create the ingredient in a Dto: its name and its quantity
     * @return A 201 HTTP code or a 400 if bad request
     */
    @Operation(summary = "Add a new ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addIngredient(@RequestBody IngredientRequestDto requestDto);

    /**
     *<p>PatchIngredient methode handles the modification of an ingredient for restocking </p>
     *
     * @param ingredientName Name of the ingredient
     * @param patchDto The desired quantity for restocking from client in a Dto
     * @return  A 200 HTTP code and a response entity with an ingredient in a Dto or a 404 code if not found
     */
    @Operation(summary = "Modify an ingredient or its stock quantity (PATCH)")
    @ApiResponse(responseCode = "200", description = "Ingredient successfully updated")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{name}")
    ResponseEntity<IngredientResponseDto> patchIngredient(@Parameter(description = "Ingredient name not found", required = true) @PathVariable("name") String ingredientName, @RequestBody IngredientPatchDto patchDto);
}
