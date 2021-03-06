package com.msebela.scraping.configuration;

import com.msebela.scraping.article.ArticleInfoRepository;
import com.msebela.scraping.article.ArticleSearchService;
import com.msebela.scraping.scraper.ArticleScraper;
import com.msebela.scraping.scraper.WebScraper;
import com.msebela.scraping.scraper.WebScraperService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableScheduling
public class SpringConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "scraper.articles")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public ArticleScraper articleScraper(final ApplicationProperties applicationProperties) {
        return new WebScraper(applicationProperties);
    }

    @Bean
    public WebScraperService webScraperService(final ArticleInfoRepository articleInfoRepository,
                                               final ArticleScraper articleScraper) {
        return new WebScraperService(articleInfoRepository, articleScraper);
    }

    @Bean
    public ScheduledTask scheduledTask(
            final WebScraperService webScraperService, final ApplicationProperties applicationProperties) {
        return new ScheduledTask(webScraperService, applicationProperties);
    }

    @Bean
    public ArticleSearchService articleSearchService(final EntityManagerFactory entityManagerFactory) {
        final ArticleSearchService searchService =
                new ArticleSearchService(entityManagerFactory.createEntityManager());
        searchService.indexEntities();
        return searchService;
    }

}
