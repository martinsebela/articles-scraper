package com.msebela.scraping.scraper;

import com.msebela.scraping.configuration.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class ScraperService {
    private final Scraper scraper;
    private final ApplicationProperties applicationProperties;

    @Scheduled(fixedRateString = "${articles.scraper.task-interval-seconds:60}", timeUnit = TimeUnit.SECONDS)
    public void scrapeWebs() {
        log.info("Begin scraping web");
    }
}
