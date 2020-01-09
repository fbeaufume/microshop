package com.adeliosys.microshop.order.service;

import com.adeliosys.microshop.common.exception.NotEnoughException;
import com.adeliosys.microshop.common.exception.NotFoundException;
import com.adeliosys.microshop.common.exception.ServerException;
import com.adeliosys.microshop.order.model.Article;
import com.adeliosys.microshop.order.model.LineItem;
import com.adeliosys.microshop.order.model.Order;
import com.adeliosys.microshop.order.model.OrderStatus;
import com.adeliosys.microshop.order.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private RestTemplate restTemplate;

    private OrderRepository repository;

    public OrderService(RestTemplate restTemplate, OrderRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public void initData() {
        LOGGER.info("Initializing the database");

        repository.deleteAll();
    }

    public Iterable<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Order.class, id));
    }

    /**
     * Create a new order and update the stock.
     */
    @HystrixCommand(fallbackMethod = "createOrderFallback", ignoreExceptions = NotEnoughException.class)
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.VALIDATED);
        logOrderCreation(order);

        // For each item, get the current price and update the stock count
        for (LineItem item : order.getItems()) {
            // Current impl does not revert the stock count updates in case of a partial lack of stock
            // Could also use a proper ResponseErrorHandler with RestTemplate
            try {
                Article article = restTemplate.getForObject("http://stock/api/articles/{id}/book?quantity={qty}",
                        Article.class, item.getProductId(), item.getQuantity());
                item.setPrice(article.getPrice());
            } catch (HttpClientErrorException e) {
                // Caught when the stock service returned a BAD REQUEST status
                // meaning that there is not enough articles
                throw new NotEnoughException(Article.class);
            } catch (RestClientException e) {
                throw new ServerException("Failed to call the stock service: " + e.toString());
            }
        }

        return repository.save(order);
    }

    public Order createOrderFallback(Order order) {
        order.setStatus(OrderStatus.NEW);
        logOrderCreation(order);
        return repository.save(order);
    }

    private void logOrderCreation(Order order) {
        LOGGER.info("Creating {} order with {} article{} for '{}'", order.getStatus(), order.countItems(), order.countItems() > 1 ? "s" : "", order.getUsername());
    }
}
