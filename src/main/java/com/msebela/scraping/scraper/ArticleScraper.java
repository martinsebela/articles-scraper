package com.msebela.scraping.scraper;

import com.msebela.scraping.article.dto.ArticleInfo;

import java.util.Set;

/**
 * Interface denoting that implementing class is able to perform scraping.
 */
public interface ArticleScraper {

    /**
     * Scrape URL for information about articles.
     * @param url URL to scrape.
     * @param articleScrapeable Object performing the scraping.
     * @return Set of articles on given URL.
     */
    Set<ArticleInfo> scrape(final String url, final ArticleScrapeable articleScrapeable);
}
