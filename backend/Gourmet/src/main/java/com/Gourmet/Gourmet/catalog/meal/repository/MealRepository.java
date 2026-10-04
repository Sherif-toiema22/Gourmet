package com.Gourmet.Gourmet.catalog.meal.repository;

import com.Gourmet.Gourmet.catalog.meal.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}