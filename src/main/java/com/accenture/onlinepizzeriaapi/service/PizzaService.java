package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;

import java.util.List;
import java.util.UUID;

public interface PizzaService {

    /**
     * <p>addPizza method create a Pizza object </p>
     *
     * @param dtoRequest
     * @return PizzaResponseDto  Pizza saved in database
     * @Throw PizzaException
     *
     * @author brewen.huiban
     */
    PizzaResponseDto addPizza(PizzaRequestDto dtoRequest);

    /**
     * <p>findById method get a pizza by its Id from database</p>
     *
     * @param inputId Id of the pizza requested
     * @return PizzaResponseDto  Pizza object with the Id requested
     * @Throw EntityNotFoundException
     *
     * @author brewen.huiban
     */
    PizzaResponseDto findById(UUID inputId);

    /**
     * <p>findAll method get all pizzas from database</p>
     *
     * @return List<PizzaResponseDto>  List of all pizzas
     *
     * @author brewen.huiban
     */
    List<PizzaResponseDto> findAll();

    /**
     * <p>findByName method get a pizza by its name from database</p>
     * @param name Name of the pizza requested
     * @return PizzaResponseDto  Pizza object with the name requested
     *
     * @author brewen.huiban
     */
    PizzaResponseDto findByName(String name);


    /**
     * <p>checkPizza method verify all arguments from a user request </p>
     *
     * @param any
     *
     * @author brewen.huiban
     */
    void checkPizza(PizzaRequestDto any);
}
