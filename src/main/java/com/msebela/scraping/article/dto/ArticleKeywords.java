package com.msebela.scraping.article.dto;

import lombok.NonNull;

import java.util.List;

/**
 * Record holding information about keywords to search for articles.
 * @param keywords List of keywords.
 */
public record ArticleKeywords(
        @NonNull List<String> keywords
) {
}
