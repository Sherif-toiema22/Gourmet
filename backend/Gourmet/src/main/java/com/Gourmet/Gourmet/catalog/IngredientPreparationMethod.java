package com.Gourmet.Gourmet.catalog;

import com.Gourmet.Gourmet.catalog.Ingredient;
import com.Gourmet.Gourmet.catalog.PreparationMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredient_preparation_methods")
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