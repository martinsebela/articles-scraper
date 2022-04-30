package com.msebela.scraping.configuration;

import com.msebela.scraping.websites.WebsiteInfo;
import lombok.Data;

import java.util.Set;

@Data
public class ApplicationProperties {

    /**
     *  Set of websites that should be scraped.
     */
    private Set<WebsiteInfo> websites;

    /**
     *  Maximum time for attempt to connect to get a website.
     */
    private int connectTimeoutSeconds;
}
