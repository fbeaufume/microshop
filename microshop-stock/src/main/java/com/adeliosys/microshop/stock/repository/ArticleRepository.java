package com.adeliosys.microshop.stock.repository;

import com.adeliosys.microshop.stock.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
}
