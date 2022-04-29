package com.msebela.articles.configuration;

import com.msebela.articles.scraper.ScraperTask;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "articles.application")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public ScraperTask scraperTask() {
        return new ScraperTask();
    }

}
