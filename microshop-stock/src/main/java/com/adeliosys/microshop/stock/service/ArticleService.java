package com.adeliosys.microshop.stock.service;

import com.adeliosys.microshop.common.exception.NotFoundException;
import com.adeliosys.microshop.stock.model.Article;
import com.adeliosys.microshop.stock.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    private ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initData() {
        LOGGER.info("Initializing the database");

        List<Article> articles = Arrays.asList(
                new Article("1", "Article 1", 100.0, 40),
                new Article("2", "Article 2", 200.0, 80),
                new Article("3", "Article 3", 300.0, 60)
        );

        repository.saveAll(articles);
    }

    public List<Article> getArticles() {
        return repository.findAll();
    }

    public Article getArticle(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Article.class, id));
    }

    public Article saveArticle(Article article) {
        return repository.save(article);
    }
}
