package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.exception.IngredientException;
import com.accenture.onlinepizzeriaapi.mapper.IngredientMapper;
import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.repository.IngredientDao;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;
    private final MessageSourceAccessor messages;

    @Override
    @Transactional(readOnly = true)
    public List<IngredientResponseDto> findAll() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDto findById() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto requestDto) throws IngredientException{
        log.info("Entering in the method that creates a new ingredient in stock with this input ={}",requestDto);

        checkIngredient(requestDto);
        Ingredient saved = ingredientDao.save(ingredientMapper.toIngredient(requestDto));
        return ingredientMapper.toIngredientResponseDto(saved);
    }

    @Override
    public void deleteIngredient(UUID idIngredient) {

    }

    @Override
    public IngredientResponseDto patchIngredient(UUID idIngredient, IngredientRequestDto requestDto) {
        return null;
    }

    public void checkIngredient(IngredientRequestDto requestDto) {
        if (requestDto == null)
            throw new IngredientException(messages.getMessage("ingredient.null"));
    }
}
