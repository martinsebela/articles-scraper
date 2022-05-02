package com.msebela.scraping.article;

import com.msebela.scraping.article.dto.ArticleKeywords;
import com.msebela.scraping.article.dto.ArticlesResult;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/articles", produces = "application/json")
@AllArgsConstructor
public class ArticleController {
    private final ArticleSearchService articleSearchService;

    /**
     * Finds articles based on keywords.
     * @param articleKeywords Keywords to search articles by.
     * @return Information about articles matching keywords.
     */
    @PostMapping(path = "/find")
    public ResponseEntity<ArticlesResult> findArticles(@RequestBody final ArticleKeywords articleKeywords) {
        final ArticlesResult articlesResult =
                articleSearchService.searchArticlesByKeywords(articleKeywords.keywords());
        return ResponseEntity.ok(articlesResult);
    }
}
