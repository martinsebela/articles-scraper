package com.msebela.scraping.article;

import lombok.NonNull;

/**
 * Record holding information about an article.
 * @param url Article URL.
 * @param headline Article headline.
 */
public record ArticleInfo(@NonNull String url,
                          @NonNull String headline) {
}
