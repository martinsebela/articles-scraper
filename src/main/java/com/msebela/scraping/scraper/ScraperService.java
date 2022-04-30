package com.msebela.scraping.scraper;

import com.msebela.scraping.configuration.ApplicationProperties;
import com.msebela.scraping.websites.Website;
import com.msebela.scraping.websites.WebsiteInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ScraperService {
    private final Scraper scraper;
    private final ApplicationProperties applicationProperties;

    @Scheduled(fixedRateString = "${articles.scraper.task-interval-seconds:60}", timeUnit = TimeUnit.SECONDS)
    public void scrapeWebs() {
        log.info("Begin scraping web");
        final Set<Website> websites = Website.getWebsites();
        final Map<WebsiteInfo, Optional<Website>> websiteMap =
                applicationProperties.getWebsites().stream().collect(Collectors.toMap(
                        Function.identity(),
                        info -> websites.stream().filter(w -> w.getWebsiteType() == info.websiteType()).findFirst()));
        scrapeWebsites(websiteMap);
    }

    private void scrapeWebsites(final Map<WebsiteInfo, Optional<Website>> websiteMap) {
        websiteMap.forEach((info, websiteOptional) -> {
            if (websiteOptional.isPresent()) {
                Website web = websiteOptional.get();
                scraper.scrape(info.url(), web);
            } else {
                log.warn("No implementation found for website type {}." + info.websiteType());
            }
        });
    }
}
