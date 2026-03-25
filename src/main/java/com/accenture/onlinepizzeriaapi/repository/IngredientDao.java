package com.accenture.onlinepizzeriaapi.repository;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IngredientDao extends JpaRepository<Ingredient, UUID> {
    Optional<Ingredient> findByName(String name);
}
