package com.Gourmet.Gourmet.catalog.meal;

import com.Gourmet.Gourmet.catalog.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "meal_ingredients",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"meal_id", "ingredient_id"}
                )
        }
)
@Getter
@Setter
public class MealIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "meal_id",
            nullable = false
    )
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ingredient_id",
            nullable = false
    )
    private Ingredient ingredient;

    private boolean required;

    private BigDecimal minQuantity;

    private BigDecimal maxQuantity;
}