package com.msebela.scraping.article.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

import java.util.List;

/**
 * Record holding information about keywords to search for articles.
 * @param keywords List of keywords.
 */
@Schema(description = "Keywords to search for articles headlines.")
public record ArticleKeywords(
        @NonNull
        @Schema(description = "Array of keywords.", required = true, example = "[\"dog\", \"cat\"]")
        @ArraySchema(schema = @Schema(implementation = String.class))
        List<String> keywords
) {
}
