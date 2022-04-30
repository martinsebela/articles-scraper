package com.msebela.scraping.websites;

/**
 * Information to identify a website.
 * @param url Url of the website.
 * @param websiteType Type of website.
 */
public record WebsiteInfo(String url, WebsiteType websiteType) {
}
