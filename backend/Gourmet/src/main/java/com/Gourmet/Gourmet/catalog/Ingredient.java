package com.Gourmet.Gourmet.catalog;

import jakarta.persistence.*;
import org.springframework.format.annotation.DurationFormat;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private DurationFormat.Unit unit;

    private BigDecimal pricePerUnit;

    private boolean available = true;
}