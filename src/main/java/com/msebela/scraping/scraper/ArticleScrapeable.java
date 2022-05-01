package com.msebela.scraping.scraper;

import com.msebela.scraping.article.dto.ArticleInfo;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * Interface denoting that class implementing this is able to extract article information from document.
 */
public interface ArticleScrapeable {

    /**
     * Process document and extract information about articles.
     * @param document
     * @return
     */
    Set<ArticleInfo> extractArticlesFromDocument(final Document document);
}
