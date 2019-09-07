package com.adeliosys.microshop.order.rest;

import com.adeliosys.microshop.order.model.LineItem;
import com.adeliosys.microshop.order.model.Order;
import com.adeliosys.microshop.order.model.OrderStatus;
import com.adeliosys.microshop.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/orders/reset")
    public String resetOrders() {
        service.initData();
        return "ok";
    }

    @GetMapping("/orders")
    public Iterable<Order> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/orders/create")
    public Order createOrder(
            @RequestParam(required = false, defaultValue = "John Doe") String username,
            @RequestParam(required = false, defaultValue = "1") String productId,
            @RequestParam(required = false, defaultValue = "1") int quantity) {
        Order order = new Order(username, OrderStatus.NEW, Collections.singletonList(new LineItem(productId, 0.0d, quantity)));
        return service.createOrder(order);
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        return service.createOrder(order);
    }
}
