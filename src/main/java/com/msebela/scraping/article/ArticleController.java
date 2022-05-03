package com.msebela.scraping.article;

import com.msebela.scraping.article.dto.ArticleKeywords;
import com.msebela.scraping.article.dto.ArticlesResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Find articles by headline keywords")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PostMapping(path = "/find")
    public ResponseEntity<ArticlesResult> findArticles(@RequestBody final ArticleKeywords articleKeywords) {
        final ArticlesResult articlesResult =
                articleSearchService.searchArticlesByKeywords(articleKeywords.keywords());
        return ResponseEntity.ok(articlesResult);
    }
}
