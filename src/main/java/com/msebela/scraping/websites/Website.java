package com.msebela.scraping.websites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public abstract class Website implements Scrapable {

    /**
     * The type of the website.
     */
    @NonNull
    private final WebsiteType websiteType;
    /**
     * The URL of the website.
     */
    @NonNull
    private final String url;

    /**
     * Array of available website implementations.
     */
    @Getter
    private static Website[] websites = new Website[] {
            new IdnesWebsite(), new HNWebsite(), new BBCWebsite()
    };
}
