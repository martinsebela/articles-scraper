package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.configuration.ApplicationProperties;
import com.msebela.scraping.websites.Website;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Set;

@AllArgsConstructor
public class WebScraper implements Scraper {
    private final ApplicationProperties applicationProperties;

    public Set<ArticleInfo> scrape(@NonNull final Website website) {
        try {
            Jsoup.connect(website.getUrl()).timeout(applicationProperties.getConnectTimeoutSeconds()).get();
        } catch (SocketTimeoutException e) {
            throw  new RuntimeException(e); //TODO
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO
        }

        return null;
    }
}
