package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public abstract class Website implements Scrapable {

    /**
     * The type of the website.
     */
    @NonNull
    private final WebsiteType websiteType;

    /**
     * List of available website implementations.
     */
    @Getter
    private static Set<Website> websites = Set.of(
            new IdnesWebsite(), new HNWebsite(), new BBCWebsite());

}
