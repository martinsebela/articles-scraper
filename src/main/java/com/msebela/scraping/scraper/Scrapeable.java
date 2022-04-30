package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfo;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 *
 */
public interface Scrapeable {

    /**
     * Process document and extract information about articles.
     * @param document
     * @return
     */
    Set<ArticleInfo> extractArticlesFromDocument(final Document document);
}
