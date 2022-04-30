package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.websites.Website;
import lombok.NonNull;

import java.util.Set;

public interface Scraper {
    public Set<ArticleInfo> scrape(final String url, final Website website);
}
