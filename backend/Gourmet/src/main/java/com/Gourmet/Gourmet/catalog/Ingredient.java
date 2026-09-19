package com.Gourmet.Gourmet.catalog;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String unit;

    private BigDecimal basePrice;

    private boolean available = true;

    @OneToMany(
            mappedBy = "ingredient"
    )
    private List<MealIngredient> meals = new ArrayList<>();
}