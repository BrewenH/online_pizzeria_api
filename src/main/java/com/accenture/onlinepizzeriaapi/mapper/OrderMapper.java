package com.accenture.onlinepizzeriaapi.mapper;

import com.accenture.onlinepizzeriaapi.model.Order;
import com.accenture.onlinepizzeriaapi.service.dto.OrderRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);
}
