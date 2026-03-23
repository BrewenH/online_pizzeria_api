package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.controller.advice.ErrorDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import com.accenture.onlinepizzeriaapi.service.dto.OrderRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.OrderResponseDto;
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

@Tag(name = "Orders", description = "Pizza orders Management API")
@RequestMapping("/orders")
public interface OrderApi {

    @Operation(summary = "Find all orders")
    @ApiResponse(responseCode = "200", description = "List all orders")
    @GetMapping
    ResponseEntity<List<OrderResponseDto>> findAll();

    @Operation(summary = "Get an order by its id")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDto> findById(@Parameter(description = "Order id not found", required = true) @PathVariable("id") UUID idOrder);

    @Operation(summary = "Add a new order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Order request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addOrder(@RequestBody OrderRequestDto requestDto);

    @Operation(summary = "Delete an order by its id")
    @ApiResponse(responseCode = "204", description = "Order deleted")
    @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@Parameter(description = "Order id not found", required = true) @PathVariable("id") UUID idOrder);

    @Operation(summary = "Partially modify an order (PATCH)")
    @ApiResponse(responseCode = "200", description = "Order successfully updated")
    @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    ResponseEntity<IngredientResponseDto> patchOrder(@Parameter(description = "Order id not found", required = true) @PathVariable("id") UUID idOrder, @RequestBody OrderRequestDto requestDto);
}
