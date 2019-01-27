package com.adeliosys.microshop.order.service;

import com.adeliosys.microshop.common.exception.NotFoundException;
import com.adeliosys.microshop.order.model.Order;
import com.adeliosys.microshop.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Iterable<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Order saveOrder(Order order) {
        return repository.save(order);
    }
}
