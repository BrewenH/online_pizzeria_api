package com.accenture.onlinepizzeriaapi.model;

import com.accenture.onlinepizzeriaapi.model.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Pizza> pizzas;
    private EStatus status;
    private LocalDate date;
    private double totalPrice;

    public Order(List<Pizza> pizzas, EStatus status, LocalDate date, double totalPrice) {
        this.pizzas = pizzas;
        this.status = EStatus.PENDING;
        this.date = date;
        this.totalPrice = totalPrice;
    }
}
