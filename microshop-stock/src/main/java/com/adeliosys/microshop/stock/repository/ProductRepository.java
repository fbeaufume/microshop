package com.adeliosys.microshop.stock.repository;

import com.adeliosys.microshop.stock.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
