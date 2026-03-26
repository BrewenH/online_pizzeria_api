package com.accenture.onlinepizzeriaapi.service;


import com.accenture.onlinepizzeriaapi.exception.PizzaException;
import com.accenture.onlinepizzeriaapi.mapper.PizzaMapper;
import com.accenture.onlinepizzeriaapi.model.Pizza;
import com.accenture.onlinepizzeriaapi.repository.PizzaDao;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final MessageSourceAccessor messages;

    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto dtoRequest) throws PizzaException {
        checkPizza(dtoRequest);
        Pizza saved = pizzaDao.save(pizzaMapper.toPizza(dtoRequest));
        return pizzaMapper.toPizzaResponseDto(saved);
    }

    @Override
    public void checkPizza(PizzaRequestDto dtoRequest) {
        if (dtoRequest == null)
            throw new PizzaException(messages.getMessage("pizza.null"));
    }

    @Override
    public PizzaResponseDto findById(UUID id) {
        Pizza optPizza = null;
        try {
            optPizza = pizzaDao.getReferenceById(id);
        } catch (EntityNotFoundException _) {
            throw new EntityNotFoundException(messages.getMessage("pizza.id.not-found"));
        }

        return pizzaMapper.toPizzaResponseDto(optPizza);
    }

    @Override
    public List<PizzaResponseDto> findAll() {
        List<Pizza> pizzas = pizzaDao.findAll();
        return pizzas.stream()
                .map(pizzaMapper::toPizzaResponseDto)
                .toList();
    }

    @Override
    public PizzaResponseDto findByName(String name) {
        Optional<Pizza> pizza = null;

        pizza = pizzaDao.findByName(name);

        return pizzaMapper.toPizzaResponseDto(pizza.orElseThrow(() -> new EntityNotFoundException(messages.getMessage("pizza.name.not-found"))));

    }
}
