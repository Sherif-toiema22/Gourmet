package com.Gourmet.Gourmet.catalog;

import jakarta.persistence.*;

@Entity
@Table(name = "preparation_methods")
public class PreparationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
}