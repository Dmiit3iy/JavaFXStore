package com.dmiit3iy.javafxStore.domain;

import java.util.HashMap;
import java.util.Objects;

public class ShoppingCart {
    private User user;
    private HashMap<Product, Integer> cart = new HashMap<>();
    private CartStatus cartStatus;

    public ShoppingCart(User user, HashMap<Product, Integer> cart) {
        this.user = user;
        this.cart = cart;
        cartStatus = CartStatus.InProgress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<Product, Integer> cart) {
        this.cart = cart;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCart that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(cart, that.cart) && cartStatus == that.cartStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, cart, cartStatus);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "user=" + user +
                ", cart=" + cart +
                ", cartStatus=" + cartStatus +
                '}';
    }
}
