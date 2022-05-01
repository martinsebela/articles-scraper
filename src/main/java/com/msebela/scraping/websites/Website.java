package com.msebela.scraping.websites;

import com.msebela.scraping.article.dto.ArticleInfo;
import com.msebela.scraping.scraper.ArticleScrapeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides functionality to extract information from particular website.
 */
@Getter
@AllArgsConstructor
@Slf4j
public abstract class Website implements ArticleScrapeable {

    /**
     * List of available website implementations.
     */
    @Getter
    private static Set<Website> websites = Set.of(
            new IdnesWebsite(), new HNWebsite(), new BBCWebsite());

    private static final String WEBSITE_URL_XPATH = "/html//meta[@property='og:url']";

    private static final String WEBSITE_URL_ATTRIBUTE = "content";

    /**
     * The type of the website.
     */
    @NonNull
    private final WebsiteType websiteType;

    /**
     * Obtains information about articles from HTML document.
     * @param document HTML document.
     * @return Set of obtained article information.
     */
    @Override
    public Set<ArticleInfo> extractArticlesFromDocument(Document document) {
        final Elements elements = extractArticles(document);
        final Optional<String> websiteUrl = extractWebsiteUrl(document);
        final Set<ArticleInfo> foundArticles =
                elements.stream().map(e -> extractArticleInfo(e, websiteUrl)).
                        filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

        return foundArticles;
    }

    /**
     * Obtains information from single article element.
     * @param element Article element.
     * @param websiteUrl Website URL.
     * @return Optional article information, empty if obtaining data was not successful.
     */
    protected Optional<ArticleInfo> extractArticleInfo(final Element element, final Optional<String> websiteUrl) {
        final Optional<String> articleUrl = extractUrlFromArticle(element, websiteUrl);
        if (articleUrl.isEmpty()) {
            log.trace("Cannot obtain link from article element: {}.", element);
            return Optional.empty();
        }
        final Optional<String> headline = extractHeadlineFromArticle(element);
        if (headline.isEmpty()) {
            log.trace("Cannot obtain headline from article element: {}.", element);
            return Optional.empty();
        }
        return Optional.of(new ArticleInfo(articleUrl.get(), headline.get()));
    }

    /**
     * Obtains website URL from HTML document.
     * @param document HTML document.
     * @return Optional website url, empty if obtaining was not successful.
     */
    protected Optional<String> extractWebsiteUrl(Document document) {
        final Element urlProperty = document.selectXpath(WEBSITE_URL_XPATH).first();
        if (urlProperty == null) {
            log.trace("Cannot obtain website url from document");
            return Optional.empty();
        }
        return Optional.ofNullable(urlProperty.attributes().get(WEBSITE_URL_ATTRIBUTE));
    }

    /**
     * Obtains elements of articles in HTML document.
     * @param document HTML document.
     * @return Elements of articles in the document.
     */
    protected abstract Elements extractArticles(final Document document);

    /**
     * Obtains article URL from element.
     * @param element Article element.
     * @param websiteUrl URL of the website.
     * @return Optional article URL, empty if obtaining was not successful.
     */
    protected abstract Optional<String> extractUrlFromArticle(final Element element, final Optional<String> websiteUrl);

    /**
     * Obtains headline from article element.
     * @param element Article element.
     * @return Optional headline, emptyh if obtaining was not successful.
     */
    protected abstract Optional<String> extractHeadlineFromArticle(final Element element);

}
