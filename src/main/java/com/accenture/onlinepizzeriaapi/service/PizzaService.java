package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;

public interface PizzaService {
    PizzaResponseDto addPizza(PizzaRequestDto dtoRequest);

    void checkPizza(PizzaRequestDto any);
}
