package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.exception.IngredientException;
import com.accenture.onlinepizzeriaapi.mapper.IngredientMapper;
import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.repository.IngredientDao;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import com.accenture.onlinepizzeriaapi.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;
    private final MessageSourceAccessor messages;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<IngredientResponseDto> findAll() {
        log.info("Entering in the method that lists all ingredients in stock");

        List<Ingredient> ingredients = ingredientDao.findAll();

        return ingredients.stream()
                .map(ingredient -> ingredientMapper.toIngredientResponseDto(ingredient))
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDto findById(UUID idIngredient) {
        log.info("Entering in the method that finds ingredient in stock with input id= {}", idIngredient);
        Optional<Ingredient> ingredient = null;

            ingredient = ingredientDao.findById(idIngredient);

        return ingredientMapper.toIngredientResponseDto(ingredient.orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.INGREDIENT_ID_NOT_FOUND))));
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDto findByName(String name) {
        log.info("Entering in the method that finds ingredient in stock with input name= {}", name);
        Optional<Ingredient> ingredient = null;

        ingredient = ingredientDao.findByName(name);

        return ingredientMapper.toIngredientResponseDto(ingredient.orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.INGREDIENT_NAME_NOT_FOUND))));
    }

    /** {@inheritDoc} */
    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto requestDto) throws IngredientException{
        log.info("Entering in the method that creates a new ingredient in stock with this request Dto ={}",requestDto);

        checkIngredient(requestDto);
        Ingredient saved = ingredientDao.save(ingredientMapper.toIngredient(requestDto));
        return ingredientMapper.toIngredientResponseDto(saved);
    }
    
    /** {@inheritDoc} */
    @Override
    public IngredientResponseDto patchIngredient(String name, IngredientPatchDto patchDto) {
        log.info("Entering in the method that updates ingredient quantity in stock with input name= {} and dto={}", name, patchDto);

        Optional<Ingredient> optIngredient = ingredientDao.findByName(name);
        if(optIngredient.isEmpty())
            throw new EntityNotFoundException(messages.getMessage(Messages.INGREDIENT_ID_NOT_FOUND));

        Ingredient ingredient = optIngredient.get();
        if (patchDto == null || patchDto.quantity()  <1)
            throw new IngredientException(messages.getMessage(Messages.INGREDIENT_QUANTITY_MINIMUM));

        ingredient.setQuantity(patchDto.quantity());


        Ingredient updated = ingredientDao.save(ingredient);
        return ingredientMapper.toIngredientResponseDto(updated);
    }

    public void checkIngredient(IngredientRequestDto requestDto) {
        if (requestDto == null)
            throw new IngredientException(messages.getMessage(Messages.INGREDIENT_NULL));
    }
}
