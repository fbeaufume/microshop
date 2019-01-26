package com.adeliosys.microshop.stock.service;

import com.adeliosys.microshop.stock.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {

    private MongoRepository repository;

    public ArticleService(MongoRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initData() {
        List<Article> articles = Arrays.asList(
                new Article(null, "Article 1", 100.0, 40),
                new Article(null, "Article 2", 200.0, 80),
                new Article(null, "Article 3", 300.0, 60)
        );

        repository.saveAll(articles);
    }

    public List getArticles() {
        return repository.findAll();
    }
}
