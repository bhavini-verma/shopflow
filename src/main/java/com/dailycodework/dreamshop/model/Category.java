package com.dailycodework.dreamshop.model;

import jakarta.persistence.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}