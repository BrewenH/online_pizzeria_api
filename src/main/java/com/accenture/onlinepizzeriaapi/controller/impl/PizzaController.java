package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.PizzaApi;
import com.accenture.onlinepizzeriaapi.service.PizzaService;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PizzaController implements PizzaApi {

    private final PizzaService pizzaService;

    @Override
    public ResponseEntity<List<PizzaResponseDto>> findAll() {
        return ResponseEntity.ok(pizzaService.findAll());
    }

    @Override
    public ResponseEntity<PizzaResponseDto> findById(UUID idPizza) {
        return ResponseEntity.ok(pizzaService.findById(idPizza));
    }

    @Override
    public ResponseEntity<PizzaResponseDto> findByName(String name) {
        return ResponseEntity.ok(pizzaService.findByName(name));
    }

    @Override
    public ResponseEntity<Void> addPizza(PizzaRequestDto requestDto) {
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
