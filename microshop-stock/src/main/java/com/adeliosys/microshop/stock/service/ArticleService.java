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

        repository.deleteAll();

        repository.saveAll(Arrays.asList(
                new Article("1", "CD", 10.0d, 1000),
                new Article("2", "Scarf", 30.0d, 1000),
                new Article("3", "Blender", 100.0d, 1000)));
    }

    public List<Article> getArticles() {
        return repository.findAll();
    }

    public Article getArticle(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Article.class, id));
    }

    /**
     * Book articles, used when registering an order.
     */
    public Article bookArticle(String id, int quantity) {
        LOGGER.info("Booking {} instance{} of article {}", quantity, quantity > 1 ? "s" : "", id);
        Article article = getArticle(id);
        article.decreaseQuantity(quantity); // Throws an exception if the stock is too low
        saveArticle(article);
        return article;
    }

    public Article saveArticle(Article article) {
        return repository.save(article);
    }
}
