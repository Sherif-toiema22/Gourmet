package com.Gourmet.Gourmet.catalog.meal.repository;

import com.Gourmet.Gourmet.catalog.meal.MealIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealIngredientRepository
        extends JpaRepository<MealIngredient, Long> {

    Optional<MealIngredient> findByMealIdAndIngredientId(
            Long mealId,
            Long ingredientId
    );
}