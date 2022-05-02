package com.msebela.scraping.scraper.websites;

import java.net.URL;

/**
 * Information to identify a website.
 * @param url Url of the website.
 * @param websiteType Type of website.
 */
public record WebsiteInfo(URL url, WebsiteType websiteType) {
}
