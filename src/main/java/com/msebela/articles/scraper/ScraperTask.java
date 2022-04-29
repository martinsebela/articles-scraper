package com.msebela.articles.scraper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class ScraperTask {

    @Scheduled(fixedRateString = "${articles.scraper.task-interval-seconds}", timeUnit = TimeUnit.SECONDS)
    public void scrapeWebs() {
        log.info("Begin scraping web");
    }
}
