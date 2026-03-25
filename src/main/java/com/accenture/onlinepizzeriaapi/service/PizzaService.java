package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;

import java.util.List;
import java.util.UUID;

public interface PizzaService {
    PizzaResponseDto addPizza(PizzaRequestDto dtoRequest);

    void checkPizza(PizzaRequestDto any);

    PizzaResponseDto findById(UUID inputId);

    List<PizzaResponseDto> findAll();
}
