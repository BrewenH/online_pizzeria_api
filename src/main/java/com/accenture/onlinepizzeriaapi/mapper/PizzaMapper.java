package com.accenture.onlinepizzeriaapi.mapper;

import com.accenture.onlinepizzeriaapi.model.Pizza;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza toPizza(PizzaRequestDto pizzaRequestDto);

    PizzaResponseDto toPizzaResponseDto(Pizza pizza);
}
