package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.scraper.Scrapeable;
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

@Getter
@AllArgsConstructor
@Slf4j
public abstract class Website implements Scrapeable {

    /**
     * List of available website implementations.
     */
    @Getter
    private static Set<Website> websites = Set.of(
            new IdnesWebsite(), new HNWebsite(), new BBCWebsite());

    private static final String WEBSITE_URL_XPATH = "/html/head/meta[@property='og:url']";

    private static final String WEBSITE_URL_ATTRIBUTE = "content";

    /**
     * The type of the website.
     */
    @NonNull
    private final WebsiteType websiteType;

    @Override
    public Set<ArticleInfo> extractArticlesFromDocument(Document document) {
        final Elements elements = extractArticles(document);
        final Optional<String> websiteUrl = extractWebsiteUrl(document);
        final Set<ArticleInfo> foundArticles =
                elements.stream().map(e -> extractArticleInfo(e, websiteUrl)).
                        filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

        return foundArticles;
    }

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

    protected Optional<String> extractWebsiteUrl(Document document) {
        final Element urlProperty = document.selectXpath(WEBSITE_URL_XPATH).first();
        if (urlProperty == null) {
            log.trace("Cannot obtain website url from document");
            return Optional.empty();
        }
        return Optional.ofNullable(urlProperty.attributes().get(WEBSITE_URL_ATTRIBUTE));
    }

    protected abstract Elements extractArticles(final Document document);

    protected abstract Optional<String> extractUrlFromArticle(final Element element, final Optional<String> websiteUrl);

    protected abstract Optional<String> extractHeadlineFromArticle(final Element element);

}
