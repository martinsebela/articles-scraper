package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfo;

import java.util.Set;

public interface Scraper {
    Set<ArticleInfo> scrape(final String url, final Scrapeable scrapable);
}
