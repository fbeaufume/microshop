package com.adeliosys.microshop.stock.model;

import com.adeliosys.microshop.common.exception.NotEnoughException;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Article {

    @Id
    private String id;

    private String name;

    private double price;

    private int quantity;

    public Article() {
    }

    public Article(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int quantity) {
        // Check the remaining quantity
        if (this.quantity < quantity) {
            throw new NotEnoughException(Article.class);
        }
        else {
            this.quantity -= quantity;
        }
    }
}
