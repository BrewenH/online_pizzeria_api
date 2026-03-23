package com.accenture.onlinepizzeriaapi.controller.impl;

import com.accenture.onlinepizzeriaapi.controller.OrderApi;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import com.accenture.onlinepizzeriaapi.service.dto.OrderRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.OrderResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class OrderController implements OrderApi {
    @Override
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<OrderResponseDto> findById(UUID idOrder) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addOrder(OrderRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOrder(UUID idOrder) {
        return null;
    }

    @Override
    public ResponseEntity<IngredientResponseDto> patchOrder(UUID idOrder, OrderRequestDto requestDto) {
        return null;
    }
}
