package com.msebela.scraping.article.dto;

import java.util.List;

/**
 * Record holding information about multiple articles.
 * @param articles List of articles.
 */
public record ArticlesResult(
    List<ArticleInfo> articles
) {
}
