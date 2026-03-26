package com.accenture.onlinepizzeriaapi.model;

import com.accenture.onlinepizzeriaapi.model.enums.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Pizza is the class that we use to manage pizza objects in database
 * @author brewen.huiban
 */
@Entity
@Data
@NoArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The public name of a pizza
     */
    String name;

    /**
     * The public size of a pizza
     */
    Size size;

    /**
     * The public price of a pizza
     */
    Double price;

    @ManyToMany
    @JoinTable(name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    List<Ingredient> ingredients;
    Boolean removedFromMenu;

    public Pizza(String name, Size size, Double price, List<Ingredient> ingredients, Boolean removedFromMenu) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
        this.removedFromMenu = removedFromMenu;
    }
}
