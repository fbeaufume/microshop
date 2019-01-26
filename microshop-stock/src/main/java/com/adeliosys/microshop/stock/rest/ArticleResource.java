package com.adeliosys.microshop.stock.rest;

import com.adeliosys.microshop.stock.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleResource {

    private ArticleService service;

    public ArticleResource(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    public List getArticles() {
        return service.getArticles();
    }
}
