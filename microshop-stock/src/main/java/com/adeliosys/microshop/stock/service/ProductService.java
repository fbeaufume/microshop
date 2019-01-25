package com.adeliosys.microshop.stock.service;

import com.adeliosys.microshop.stock.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private MongoRepository repository;

    public ProductService(MongoRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initData() {
        List<Product> products = Arrays.asList(
                new Product(null, "Product 1", 100.0, 40),
                new Product(null, "Product 2", 200.0, 80),
                new Product(null, "Product 3", 300.0, 60)
        );

        repository.saveAll(products);
    }

    public List getProducts() {
        return repository.findAll();
    }
}
