package com.msebela.scraping.scraper;

import com.msebela.scraping.article.dto.ArticleInfo;
import com.msebela.scraping.configuration.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class WebScraper implements ArticleScraper {
    private final ApplicationProperties applicationProperties;

    public Set<ArticleInfo> scrape(@NonNull final URL url, @NonNull final ArticleScrapeable articleScrapeable) {
        final Set<ArticleInfo> extractedArticles;
        try {
            log.info("Attempting to connect to URL {}", url);
            final Document document = Jsoup.connect(url.toString())
                    .timeout(applicationProperties.getConnectTimeoutSeconds() * 1000).get();
            extractedArticles = articleScrapeable.extractArticlesFromDocument(document);
        } catch (SocketTimeoutException e) {
            log.error("Request to get document on URL timed out.", e);
            return Collections.emptySet();
        } catch (IOException e) {
            log.error("Unable to connect to the website.", e);
            return Collections.emptySet();
        }

        return extractedArticles;
    }
}
