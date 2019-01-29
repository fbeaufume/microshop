package com.adeliosys.microshop.order.service;

import com.adeliosys.microshop.common.exception.NotFoundException;
import com.adeliosys.microshop.order.model.LineItem;
import com.adeliosys.microshop.order.model.Order;
import com.adeliosys.microshop.order.model.OrderStatus;
import com.adeliosys.microshop.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class OrderService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // Do not use a @PostConstruct method as it does not support @Transaction natively
    // Do not use data.sql as it is usually not sequence friendly
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("Initializing the database");

        repository.deleteAll();

        Order order = new Order("John Doe", OrderStatus.NEW,
                Arrays.asList(
                        new LineItem("1", 100.0d, 1),
                        new LineItem("2", 50.0d, 1)));
        repository.save(order);
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
