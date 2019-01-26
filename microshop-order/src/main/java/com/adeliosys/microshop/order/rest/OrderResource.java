package com.adeliosys.microshop.order.rest;

import com.adeliosys.microshop.order.model.Order;
import com.adeliosys.microshop.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private OrderService service;

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public Iterable<Order> getOrders() {
        return service.getOrders();
    }
}
