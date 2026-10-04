package com.Gourmet.Gourmet.catalog.ingredient.repository;

import com.Gourmet.Gourmet.catalog.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository
        extends JpaRepository<Ingredient, Long> {
}