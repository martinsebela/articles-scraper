package com.msebela.scraping.scraper;

import com.msebela.scraping.article.ArticleInfoEntity;
import com.msebela.scraping.article.ArticleInfoRepository;
import com.msebela.scraping.article.dto.ArticleInfo;
import com.msebela.scraping.scraper.websites.Website;
import com.msebela.scraping.scraper.websites.WebsiteInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class WebScraperService {
    private final ArticleInfoRepository repository;
    private final ArticleScraper articleScraper;

    /**
     * Scrapes specified websites and stores obtained information about articles.
     * @param websites Websites to be scraped.
     */
    public void scrapeWebsAndSaveResults(final Set<WebsiteInfo> websites) {
        log.info("Begin scraping web");
        final Set<Website> existingWebsites = Website.getWebsites();
        final Map<WebsiteInfo, Optional<Website>> websiteMap =
                websites.stream().collect(Collectors.toMap(
                        Function.identity(),
                        info -> existingWebsites.stream().filter(w -> w.getWebsiteType() == info.websiteType()).findFirst()));
        final Set<ArticleInfo> articles = scrapeWebsites(websiteMap);
        saveArticles(articles);
    }

    private Set<ArticleInfo> scrapeWebsites(final Map<WebsiteInfo, Optional<Website>> websiteMap) {
        final Set<ArticleInfo> articles = new HashSet<>();
        websiteMap.forEach((info, websiteOptional) -> {
            if (websiteOptional.isPresent()) {
                final Website web = websiteOptional.get();
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
        if (articles.isEmpty()) {
            return;
        }
        Set<ArticleInfoEntity> articleEntities =
                articles.stream().map(
                        a -> new ArticleInfoEntity(null, a.url(), a.text())).collect(Collectors.toSet());
        final Set<String> existingUrls = repository.findAllByUrlIn(
                        articleEntities.stream().map(a -> a.getUrl()).collect(Collectors.toSet()))
                .stream().map(a -> a.getUrl()).collect(Collectors.toSet());
        final Set<ArticleInfoEntity> newArticles = articleEntities.stream()
                .filter(a -> !existingUrls.contains(a.getUrl())).collect(Collectors.toSet());
        repository.saveAll(newArticles);
        log.info("Saved {} new articles.", newArticles.size());
    }
}
