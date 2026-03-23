package com.accenture.onlinepizzeriaapi.repository;

import com.accenture.onlinepizzeriaapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDao extends JpaRepository<Order, UUID> {
}
