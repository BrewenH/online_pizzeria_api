package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.controller.advice.ErrorDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
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

@Tag(name = "Pizzas", description = "Online pizzeria API")
@RequestMapping("/pizzas")
public interface PizzaApi {
    @Operation(summary = "Read all pizzas")
    @ApiResponse(responseCode = "200", description = "List all pizzas")
    @GetMapping
    ResponseEntity<List<PizzaResponseDto>> findAll();

    @Operation(summary = "Get a pizza by its id")
    @ApiResponse(responseCode = "200", description = "Pizza found")
    @ApiResponse(responseCode = "404", description = "Pizza not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    ResponseEntity<PizzaResponseDto> findById(@Parameter(description = "Pizza id not found", required = true) @PathVariable("id") UUID idPizza);

    @Operation(summary = "Add a new pizza")
    @ApiResponse(responseCode = "200", description = "Pizza found")
    @ApiResponse(responseCode = "400", description = "Invalid pizza",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))

    @PostMapping
    ResponseEntity<Void> addPizza(@RequestBody PizzaRequestDto requestDto);

    @Operation(summary = "Delete a pizza by its id")
    @ApiResponse(responseCode = "204", description = "Pizza deleted")
    @ApiResponse(responseCode = "404", description = "Pizza not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePizza(@Parameter(description = "Pizza id not found", required = true) @PathVariable("id") UUID idPizza);

    @Operation(summary = "Update completely a Pizza (PUT)")
    @ApiResponse(responseCode = "200", description = "Pizza modified")
    @ApiResponse(responseCode = "404", description = "Pizza not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PutMapping("/{id}")
    ResponseEntity<PizzaResponseDto> putPizza(@Parameter(description = "Pizza id not found", required = true) @PathVariable("id") UUID idPizza, @RequestBody PizzaRequestDto requestDto);

    @Operation(summary = "Partially modify a pizza (PATCH)")
    @ApiResponse(responseCode = "200", description = "Pizza partially modified")
    @ApiResponse(responseCode = "404", description = "Pizza not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    ResponseEntity<PizzaResponseDto> patchPizza(@Parameter(description = "Pizza id not found", required = true) @PathVariable("id") UUID idPizza, @RequestBody PizzaRequestDto requestDto);
}
