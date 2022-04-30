package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import org.jsoup.nodes.Document;

import java.util.List;

public interface Scrapable {

    /**
     * Process document and extract information about articles.
     * @param document
     * @return
     */
    List<ArticleInfo> extractArticlesFromDocument(final Document document);
}
