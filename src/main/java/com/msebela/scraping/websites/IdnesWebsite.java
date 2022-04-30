package com.msebela.scraping.websites;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdnesWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH = "//a[contains(@class, 'art-link')]";

    /**
     * XPath selector to get headline text from article element.
     */
    private static final String ARTICLE_HEADLINE_XPATH = "//h3/text()";

    /**
     * HTML href attribute containing link.
     */
    private static final String LINK_ATTRIBUTE = "href";

    public IdnesWebsite() {
        super(WebsiteType.IDNES);
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
