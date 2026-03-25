package com.accenture.onlinepizzeriaapi.repository;

import com.accenture.onlinepizzeriaapi.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaDao extends JpaRepository<Pizza, UUID> {

}
