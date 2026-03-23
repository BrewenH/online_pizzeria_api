package com.accenture.onlinepizzeriaapi.model;

import com.accenture.onlinepizzeriaapi.model.enums.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    String name;
    Size size;
    Double price;
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
