package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.PizzaApi;
import com.accenture.onlinepizzeriaapi.service.PizzaService;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * RestController managing operations about pizzas
 * <p>This Controller show endpoints to create and read pizza objects</p>
 * @author brewen.huiban
 */
@Slf4j
@RestController
@AllArgsConstructor
public class PizzaController implements PizzaApi {

    private final PizzaService pizzaService;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<List<PizzaResponseDto>> findAll() {
        log.info("Access to endpoint GET/pizzas with success");
        return ResponseEntity.ok(pizzaService.findAll());
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<PizzaResponseDto> findById(UUID idPizza) {
        log.info("Access to endpoint GET/pizzas/id/{id} with success");
        return ResponseEntity.ok(pizzaService.findById(idPizza));
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<PizzaResponseDto> findByName(String name) {
        log.info("Access to endpoint GET/pizzas/{name} with success");
        return ResponseEntity.ok(pizzaService.findByName(name));
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> addPizza(PizzaRequestDto requestDto) {
        log.info("Access to endpoint /POST/pizzas");
        PizzaResponseDto responseDto = pizzaService.addPizza(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deletePizza(UUID idPizza) {
        return null;
    }

    @Override
    public ResponseEntity<PizzaResponseDto> putPizza(UUID idPizza, PizzaRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<PizzaResponseDto> patchPizza(UUID idPizza, PizzaRequestDto requestDto) {
        return null;
    }
}
