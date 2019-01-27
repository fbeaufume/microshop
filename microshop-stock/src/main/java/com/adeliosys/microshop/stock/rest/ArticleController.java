package com.adeliosys.microshop.stock.rest;

import com.adeliosys.microshop.stock.model.Article;
import com.adeliosys.microshop.stock.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    private ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return service.getArticles();
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable String id) {
        return service.getArticle(id);
    }

    @PutMapping("/articles")
    public Article updateArticle(@RequestBody Article article) {
        return service.saveArticle(article);
    }
}
