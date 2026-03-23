package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.PizzaApi;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class PizzaController implements PizzaApi {
    @Override
    public ResponseEntity<List<PizzaResponseDto>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<PizzaResponseDto> findById(UUID idPizza) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addPizza(PizzaRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePizza(UUID idPizza) {
        return null;
    }

    @Override
    public ResponseEntity<PizzaResponseDto> putPizza(UUID idPizza, PizzaRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<PizzaResponseDto> patchPizza(UUID idPizza, PizzaRequestDto requestDto) {
        return null;
    }
}
