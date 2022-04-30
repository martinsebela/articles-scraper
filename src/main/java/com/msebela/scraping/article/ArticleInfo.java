package com.msebela.scraping.article;

/**
 * Record holding information about an article.
 * @param url Article URL.
 * @param headline Article headline.
 */
public record ArticleInfo(String url,
                          String headline) {
}
