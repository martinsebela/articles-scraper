package com.msebela.scraping.article.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

import java.util.List;

/**
 * Record holding information about multiple articles.
 * @param articles List of articles.
 */
@Schema(description = "Information about multiple articles.")
public record ArticlesResult(
    @NonNull
    @Schema(description = "List of articles.", required = true)
    @ArraySchema(schema = @Schema(implementation = ArticleInfo.class))
    List<ArticleInfo> articles
) {
}
