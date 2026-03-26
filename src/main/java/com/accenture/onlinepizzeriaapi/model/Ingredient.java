package com.accenture.onlinepizzeriaapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Ingredient is the class that is used to manage the stock of ingredients in the database
 * @author Murciana Le Bouédec
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
public class Ingredient {

    /**
     * The UUID of the ingredient entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the ingredient
     */
    private String name;

    /**
     * The quantity of the ingredient
     */
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
