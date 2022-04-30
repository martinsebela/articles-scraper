package com.msebela.scraping.websites;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Optional;

public class BBCWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH =
            "//div[contains(@class, 'media__content')]//a[contains(@class, 'media')]";

    /**
     * XPath selector to get headline text from article element.
     */
    private static final String ARTICLE_HEADLINE_XPATH = "//text()";

    /**
     * HTML href attribute containing link.
     */
    private static final String LINK_ATTRIBUTE = "href";

    public BBCWebsite() {
        super(WebsiteType.BBC);
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
