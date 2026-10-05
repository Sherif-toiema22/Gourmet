package com.Gourmet.Gourmet.catalog.ingredient;

import com.Gourmet.Gourmet.catalog.meal.PreparationMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "ingredient_preparation_methods",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ingredient_id", "preparation_method_id"})
        }
)
@Getter
@Setter
public class IngredientPreparationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preparation_method_id")
    private PreparationMethod preparationMethod;

    private BigDecimal additionalPrice;
}