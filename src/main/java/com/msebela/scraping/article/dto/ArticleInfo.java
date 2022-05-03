package com.msebela.scraping.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

/**
 * Record holding information about an article.
 * @param url Article URL.
 * @param text Article headline.
 */
@Schema(description = "Information about an article.")
public record ArticleInfo(
        @NonNull
        @Schema(description = "URL of the article.", required = true)
        String url,
        @NonNull
        @Schema(description = "Headline of the article.", required = true)
        String text) {
}
