package com.msebela.scraping.websites;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BBCWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH =
            "//div[contains(@class, 'media__content')]//a[contains(@class, 'media__link')]";

    /**
     * XPath selector to get headline text from article element.
     */
    private static final String ARTICLE_HEADLINE_XPATH = "//text()";

    public BBCWebsite() {
        super(WebsiteType.BBC);
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
