package com.msebela.scraping.article;

import com.msebela.scraping.article.dto.ArticleKeywords;
import com.msebela.scraping.article.dto.ArticlesResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleSearchService articleSearchService;

    @PostMapping(path = "/find")
    public ArticlesResult findArticles(@RequestBody final ArticleKeywords articleKeywords) {
        return articleSearchService.searchArticlesByKeywords(articleKeywords.keywords());
    }
}
