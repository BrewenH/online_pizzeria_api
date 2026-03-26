package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.exception.IngredientException;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IngredientService {

    /**
     * <p>FindAll method lists all ingredients in stock and shows their names and quantity</p>
     *
     * @return List<IngredientResponseDto> List of Dtos of ingredients with their id, name and quantity
     */
    List<IngredientResponseDto> findAll();


    /**
     * <p>FindById method shows of ingredient searched with its id</p>
     *
     * @param id The id of the ingredient
     * @return The ingredient in a Dto to the client with its id, its name and its quantity
     */
    IngredientResponseDto findById(UUID id);

    /**
     * <p>FindByName  method shows of ingredient searched with its name</p>
     *
     * @param name The name of the ingredient
     * @return The ingredient in a Dto to the client with its id, its name and its quantity
     */
    IngredientResponseDto findByName(String name);

    /**
     * <p>AddIngredient  method handles the creation of a new ingredient in stock</p>
     *
     * @param requestDto The information to create the ingredient from client in a Dto: its name and its quantity
     * @return The new ingredient in a Dto to the client with its id, its name and its quantity
     */
    IngredientResponseDto addIngredient(IngredientRequestDto requestDto);

    /**
     *<p>PatchIngredient methode handles the modification of an ingredient for restocking </p>
     *
     * @param name Name of the ingredient
     * @param patchDto The desired quantity for restocking from client in a Dto
     * @return The ingredient in a Dto to the client with its id, its name and its quantity
     * @throws EntityNotFoundException If name of the ingredient is not found
     * @throws IngredientException If desired quantity is 0 or less
     */
    IngredientResponseDto patchIngredient(String name, IngredientPatchDto patchDto);

    void checkIngredient(IngredientRequestDto requestDto);
}
