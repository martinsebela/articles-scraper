package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.scraper.Scrapeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
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

    /**
     * The type of the website.
     */
    @NonNull
    private final WebsiteType websiteType;

    @Override
    public Set<ArticleInfo> extractArticlesFromDocument(Document document) {
        final Elements elements = extractArticles(document);
        final Set<ArticleInfo> foundArticles =
                elements.stream().map(this::extractArticleInfo).
                        filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

        return foundArticles;
    }

    protected Optional<ArticleInfo> extractArticleInfo(final Element element) {
        final Optional<String> url = extractUrlFromArticle(element);
        if (url.isEmpty()) {
            log.warn("Cannot obtain link from article element: {}." + element);
            return Optional.empty();
        }
        final Optional<String> headline = extractHeadlineFromArticle(element);
        if (headline.isEmpty()) {
            log.warn("Cannot obtain headline from article element: {}." + element);
            return Optional.empty();
        }
        return Optional.of(new ArticleInfo(url.get(), headline.get()));
    }

    protected abstract Elements extractArticles(final Document document);

    protected abstract Optional<String> extractUrlFromArticle(final Element element);

    protected abstract Optional<String> extractHeadlineFromArticle(final Element element);

}
