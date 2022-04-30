package com.msebela.scraping.websites;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class BBCWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private static final String ARTICLE_XPATH =
            "//div[contains(@class, 'media')][./div[contains(@class, 'media__content')]]";

    /**
     * Attribute containing headline of the article.
     */
    private static final String ARTICLE_HEADLINE_ATTRIBUTE = "data-bbc-title";

    private static final String LINK_ELEMENT_XPATH =
            "//div[contains(@class, 'media__content')]//a[contains(@class, 'media__link')]";

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
    protected Optional<String> extractUrlFromArticle(final Element element, final Optional<String> websiteUrl) {
        final Element linkElement = element.selectXpath(LINK_ELEMENT_XPATH).first();
        if (linkElement == null) {
            return Optional.empty();
        }
        final String link = linkElement.attributes().get(LINK_ATTRIBUTE);
        if (websiteUrl.isPresent()) {
            return Optional.ofNullable(getValidUrl(link, websiteUrl.get()));
        }
        return Optional.ofNullable(link);
    }

    @Override
    protected Optional<String> extractHeadlineFromArticle(final Element element) {
        return Optional.ofNullable(element.attributes().get(ARTICLE_HEADLINE_ATTRIBUTE));
    }

    private String getValidUrl(final String link, final String websiteUrl) {
        if (link != null && !link.contains(websiteUrl)) {
            return websiteUrl.replaceAll("/$", "") + link;
        }
        return link;
    }
}
