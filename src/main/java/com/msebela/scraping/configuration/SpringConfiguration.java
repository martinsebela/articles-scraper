package com.msebela.scraping.configuration;

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
    public ArticleScraper scraper() {
        return new WebScraper(applicationProperties());
    }

    @Bean
    public WebScraperService webScraperService() {
        return new WebScraperService(scraper(), applicationProperties());
    }

    @Bean
    public IndexService indexService(final EntityManagerFactory entityManagerFactory) {
        final IndexService indexService = new IndexService(entityManagerFactory);
        indexService.indexEntities();
        return indexService;
    }

}
