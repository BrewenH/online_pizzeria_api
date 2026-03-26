package com.accenture.onlinepizzeriaapi.service;




import com.accenture.onlinepizzeriaapi.exception.PizzaException;
import com.accenture.onlinepizzeriaapi.mapper.PizzaMapper;
import com.accenture.onlinepizzeriaapi.model.Pizza;

import com.accenture.onlinepizzeriaapi.repository.PizzaDao;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
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

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final MessageSourceAccessor messages;

    /** {@inheritDoc} */
    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto dtoRequest) throws PizzaException {
        log.info("Entering in the method that creates a new pizza with this input ={}", dtoRequest);
        checkPizza(dtoRequest);
        Pizza saved = pizzaDao.save(pizzaMapper.toPizza(dtoRequest));
        return pizzaMapper.toPizzaResponseDto(saved);
    }

    /** {@inheritDoc} */
    @Override
    public PizzaResponseDto findById(UUID id) {
        log.info("Entering in the method that finds pizza with input id= {}", id);
        Pizza optPizza = null;
        try {
            optPizza = pizzaDao.getReferenceById(id);
        } catch (EntityNotFoundException _) {
            throw new EntityNotFoundException(messages.getMessage(Messages.PIZZA_ID_NOT_FOUND));
        }

        return pizzaMapper.toPizzaResponseDto(optPizza);
    }

    /** {@inheritDoc} */
    @Override
    public List<PizzaResponseDto> findAll() {
        log.info("Entering in the method that lists all pizzas");
        List<Pizza> pizzas = pizzaDao.findAll();
        return pizzas.stream()
                .map(pizzaMapper::toPizzaResponseDto)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public PizzaResponseDto findByName(String name) {
        log.info("Entering in the method that finds pizza with input name= {}", name);
        Optional<Pizza> pizza = null;

        pizza = pizzaDao.findByName(name);

        return pizzaMapper.toPizzaResponseDto(pizza.orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.PIZZA_NAME_NOT_FOUND))));

    }

    /** {@inheritDoc} */
    @Override
    public void checkPizza(PizzaRequestDto dtoRequest) {
        if (dtoRequest == null)
            throw new PizzaException(messages.getMessage(Messages.PIZZA_NULL));
    }
}
