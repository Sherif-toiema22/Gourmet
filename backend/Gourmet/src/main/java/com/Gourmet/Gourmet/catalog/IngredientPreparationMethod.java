package com.Gourmet.Gourmet.catalog;

import com.Gourmet.Gourmet.catalog.Ingredient;
import com.Gourmet.Gourmet.catalog.PreparationMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "ingredient_preparation_methods",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "ingredient_id",
                                "preparation_method_id"
                        }
                )
        }
)
public class IngredientPreparationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ingredient_id",
            nullable = false
    )
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "preparation_method_id",
            nullable = false
    )
    private PreparationMethod preparationMethod;

    private BigDecimal additionalPrice;
}