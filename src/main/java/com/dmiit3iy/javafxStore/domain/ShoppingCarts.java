package com.dmiit3iy.javafxStore.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShoppingCarts {
    private LocalDateTime localDateTime;
    private int id;
    private String name;
    private ProductCategory category;
    private BigDecimal price;

    public ShoppingCarts(LocalDateTime localDateTime, int id, String name, ProductCategory category, BigDecimal price) {
        this.localDateTime = localDateTime;
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
