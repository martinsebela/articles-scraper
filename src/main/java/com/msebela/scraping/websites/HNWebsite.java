package com.msebela.scraping.websites;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HNWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH = "//article//h3[contains(@class, 'article-title')]/a";

    /**
     * XPath selector to get headline text from article element.
     */
    private static final String ARTICLE_HEADLINE_XPATH = "//text()";

    public HNWebsite() {
        super(WebsiteType.HN);
    }

    @Override
    protected String getArticleXPath() {
        return ARTICLE_XPATH;
    }

    @Override
    protected String getArticleHeadlineXPath() {
        return ARTICLE_HEADLINE_XPATH;
    }
}
