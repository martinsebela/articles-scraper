package com.msebela.scraping.websites;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Optional;

public class HNWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH = "//article//h3[contains(@class, 'article-title')]/a";

    /**
     * XPath selector to get headline text from article element.
     */
    private static final String ARTICLE_HEADLINE_XPATH = "//text()";

    /**
     * HTML href attribute containing link.
     */
    private static final String LINK_ATTRIBUTE = "href";

    public HNWebsite() {
        super(WebsiteType.HN);
    }

    @Override
    protected Elements extractArticles(Document document) {
        return document.selectXpath(ARTICLE_XPATH);
    }

    @Override
    protected Optional<String> extractUrlFromArticle(final Element element) {
        return Optional.ofNullable(element.attributes().get(LINK_ATTRIBUTE));
    }

    @Override
    protected Optional<String> extractHeadlineFromArticle(Element element) {
        final Optional<TextNode> headline =
                element.selectXpath(ARTICLE_HEADLINE_XPATH, TextNode.class).stream().findFirst();
        if (headline.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(headline.get().text());
    }
}
