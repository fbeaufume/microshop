package com.adeliosys.microshop.order.service;

import com.adeliosys.microshop.common.exception.NotEnoughException;
import com.adeliosys.microshop.common.exception.NotFoundException;
import com.adeliosys.microshop.common.exception.ServerException;
import com.adeliosys.microshop.order.model.Article;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Transactional
public class OrderService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private RestTemplate restTemplate;

    private OrderRepository repository;

    public OrderService(RestTemplate restTemplate, OrderRepository repository) {
        this.restTemplate = restTemplate;
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
                        new LineItem("1", 10.0d, 1),
                        new LineItem("3", 100.0d, 1)));
        repository.save(order);
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
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.NEW);

        // For each item, get the current price and update the stock count
        for (LineItem item : order.getItems()) {
            // Current impl does not revert the stock count updates in case of a partial lack of stock
            // Could also use a proper ResponseErrorHandler with RestTemplate
            try {
                Article article = restTemplate.getForObject("http://stock/api/articles/{id}/book?quantity={qty}",
                        Article.class, item.getProductId(), item.getQuantity());
                item.setPrice(article.getPrice());
            } catch (HttpClientErrorException e) {
                throw new NotEnoughException(Article.class);
            } catch (RestClientException e) {
                throw new ServerException("Failed to call the stock service: " + e.toString());
            }
        }

        return repository.save(order);
    }
}
