package com.msebela.scraping.article.dto;

import lombok.NonNull;

import java.util.List;

/**
 * Record holding information about multiple articles.
 * @param articles List of articles.
 */
public record ArticlesResult(
    @NonNull List<ArticleInfo> articles
) {
}
