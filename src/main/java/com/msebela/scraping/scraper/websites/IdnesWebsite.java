package com.msebela.scraping.scraper.websites;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Optional;

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
    protected Elements extractArticles(Document document) {
        return document.selectXpath(ARTICLE_XPATH);
    }

    @Override
    protected Optional<String> extractUrlFromArticle(final Element element, final Optional<String> websiteUrl) {
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
