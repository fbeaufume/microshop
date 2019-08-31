package com.adeliosys.microshop.order.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<LineItem> items;

    public Order() {
    }

    public Order(String username, OrderStatus status, List<LineItem> items) {
        this.username = username;
        this.status = status;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public int countItems() {
        return items == null ? 0 : items.size();
    }
}
