package com.dailycodework.dreamshop.request;

import java.math.BigDecimal;

public class ProductUpdateRequest {
    @Data
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
