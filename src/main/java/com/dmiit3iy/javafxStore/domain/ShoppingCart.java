package com.dmiit3iy.javafxStore.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ShoppingCart {
    private User user;


    private ArrayList<Product> productArrayList = new ArrayList<>();
    private ArrayList<LocalDateTime> localDateTime = new ArrayList<>();

    public ShoppingCart(User user, ArrayList<Product> productArrayList, ArrayList<LocalDateTime> localDateTime) {
        this.user = user;
        this.productArrayList = productArrayList;
        this.localDateTime = localDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public ArrayList<LocalDateTime> getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(ArrayList<LocalDateTime> localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCart that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(productArrayList, that.productArrayList) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, productArrayList, localDateTime);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "user=" + user +
                ", productArrayList=" + productArrayList +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
