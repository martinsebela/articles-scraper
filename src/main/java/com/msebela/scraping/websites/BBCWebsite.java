package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.List;

public class BBCWebsite extends Website {
    public BBCWebsite() {
        super(WebsiteType.BBC);
    }

    @Override
    public List<ArticleInfo> extractArticlesFromDocument(Document document) {
        return Collections.emptyList();
    }
}
