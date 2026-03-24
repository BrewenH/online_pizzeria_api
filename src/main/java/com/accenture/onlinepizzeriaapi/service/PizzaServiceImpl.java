package com.accenture.onlinepizzeriaapi.service;


import com.accenture.onlinepizzeriaapi.exception.PizzaException;
import com.accenture.onlinepizzeriaapi.mapper.PizzaMapper;
import com.accenture.onlinepizzeriaapi.model.Pizza;
import com.accenture.onlinepizzeriaapi.repository.PizzaDao;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (null == dtoRequest.name() || dtoRequest.name().isBlank())
            throw new PizzaException(messages.getMessage("pizza.name.null"));
        if (dtoRequest.size() == null)
            throw new PizzaException(messages.getMessage("pizza.size.null"));
        if (dtoRequest.price() == null)
            throw new PizzaException(messages.getMessage("pizza.price.null"));
        if (dtoRequest.ingredients() == null)
            throw new PizzaException(messages.getMessage("pizza.ingredients.null"));
        if (dtoRequest.removedFromMenu() == null)
            throw new PizzaException(messages.getMessage("pizza.removedFromMenu.null"));


    }
}
