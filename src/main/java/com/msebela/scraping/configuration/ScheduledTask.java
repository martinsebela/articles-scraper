package com.msebela.scraping.configuration;

import com.msebela.scraping.scraper.WebScraperService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * Contains scheduled task that is periodically executed.
 */
@AllArgsConstructor
public class ScheduledTask {

    private final WebScraperService webScraperService;
    private final ApplicationProperties applicationProperties;

    /**
     * Periodically invoke web scraping.
     */
    @Scheduled(fixedRateString = "${scraper.articles.task-interval-seconds:60}", timeUnit = TimeUnit.SECONDS)
    public void doWebScraping() {
        webScraperService.scrapeWebsAndSaveResults(applicationProperties.getWebsites());
    }
}
