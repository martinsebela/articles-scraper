package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.article.ArticleInfoEntity;
import com.msebela.scraping.article.ArticleInfoRepository;
import com.msebela.scraping.configuration.ApplicationProperties;
import com.msebela.scraping.websites.Website;
import com.msebela.scraping.websites.WebsiteInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class WebScraperService {
    private final ArticleInfoRepository repository;
    private final ArticleScraper articleScraper;
    private final ApplicationProperties applicationProperties;

    @Scheduled(fixedRateString = "${scraper.articles.task-interval-seconds:60}", timeUnit = TimeUnit.SECONDS)
    public void scrapeWebs() {
        log.info("Begin scraping web");
        final Set<Website> websites = Website.getWebsites();
        final Map<WebsiteInfo, Optional<Website>> websiteMap =
                applicationProperties.getWebsites().stream().collect(Collectors.toMap(
                        Function.identity(),
                        info -> websites.stream().filter(w -> w.getWebsiteType() == info.websiteType()).findFirst()));
        final Set<ArticleInfo> articles = scrapeWebsites(websiteMap);
        saveArticles(articles);
    }

    private Set<ArticleInfo> scrapeWebsites(final Map<WebsiteInfo, Optional<Website>> websiteMap) {
        final Set<ArticleInfo> articles = new HashSet<>();
        websiteMap.forEach((info, websiteOptional) -> {
            if (websiteOptional.isPresent()) {
                Website web = websiteOptional.get();
                articles.addAll(articleScraper.scrape(info.url(), web));
            } else {
                log.warn("No implementation found for website type {}.", info.websiteType());
            }
        });
        return articles;
    }

    /**
     * Saves found articles in repository. Articles with URLs present in repository are ignored.
     * @param articles Set of new articles.
     */
    @Transactional
    private void saveArticles(final Set<ArticleInfo> articles) {
        Set<ArticleInfoEntity> articleEntities =
                articles.stream().map(
                        a -> new ArticleInfoEntity(null, a.url(), a.headline())).collect(Collectors.toSet());
        final Set<String> existingUrls = repository.findAllByUrlIn(
                        articleEntities.stream().map(a -> a.getUrl()).collect(Collectors.toSet()))
                .stream().map(a -> a.getUrl()).collect(Collectors.toSet());
        final Set<ArticleInfoEntity> newArticles = articleEntities.stream()
                .filter(a -> !existingUrls.contains(a.getUrl())).collect(Collectors.toSet());
        repository.saveAll(newArticles);
        log.info("Saved {} new articles.", newArticles.size());
    }
}
