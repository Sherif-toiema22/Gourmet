package com.Gourmet.Gourmet.catalog.ingredient.repository;

import com.Gourmet.Gourmet.catalog.ingredient.IngredientPreparationMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientPreparationMethodRepository extends JpaRepository<IngredientPreparationMethod, Long> {

    Optional<IngredientPreparationMethod> findByIngredientIdAndPreparationMethodId(Long ingredientId, Long preparationMethodId);
}
