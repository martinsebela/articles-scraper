package com.msebela.scraping.scraper.websites;

import com.msebela.scraping.DocumentUtil;
import com.msebela.scraping.article.dto.ArticleInfo;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdnesWebsiteTest {

    private static final String TEST_FILE_PATH = "/websites/idnes_website.html";

    @Test
    public void testExtractArticlesFromDocument() {
        final IdnesWebsite website = new IdnesWebsite();
        final Document document = DocumentUtil.getWebsiteDocument(this, TEST_FILE_PATH);
        final Optional<String> extractedUrl = website.extractWebsiteUrl(document);
        assertTrue(extractedUrl.isPresent(), "Failed to extract website URL.");
        assertEquals("http://www.idnes.cz/", extractedUrl.get(),
                "Extracted URL does not match.");
        final Set<ArticleInfo> articleInfos = website.extractArticlesFromDocument(document);
        assertEquals(57, articleInfos.size(), "Did not find expected number of articles.");
    }

}
