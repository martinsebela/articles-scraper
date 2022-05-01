package com.msebela.scraping.configuration;

import com.msebela.scraping.scraper.ArticleScraper;
import com.msebela.scraping.scraper.WebScraperService;
import com.msebela.scraping.scraper.WebScraper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

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

}
