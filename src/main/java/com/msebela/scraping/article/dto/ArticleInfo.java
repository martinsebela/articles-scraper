package com.msebela.scraping.article.dto;

import lombok.NonNull;

/**
 * Record holding information about an article.
 * @param url Article URL.
 * @param text Article headline.
 */
public record ArticleInfo(@NonNull String url,
                          @NonNull String text) {
}
