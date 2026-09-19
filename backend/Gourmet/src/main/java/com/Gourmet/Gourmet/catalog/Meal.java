package com.Gourmet.Gourmet.catalog;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal basePrice;

    private String imageUrl;

    private boolean available = true;

    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MealIngredient> ingredients = new ArrayList<>();
}