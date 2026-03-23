package com.accenture.onlinepizzeriaapi.model;

import com.accenture.onlinepizzeriaapi.model.enums.Size;

import java.util.List;

public class Pizza {

    String name;
    Size size;
    Double price;
    List<Ingredient> ingredients;
    Boolean removedFromMenu;


}
