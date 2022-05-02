package com.msebela.scraping.scraper.websites;

import com.msebela.scraping.DocumentUtil;
import com.msebela.scraping.article.dto.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HNWebsiteTest {

    private static final String TEST_FILE_PATH = "/websites/hn_website.html";

    @Test
    public void testExtractArticlesFromDocument() {
        final HNWebsite website = new HNWebsite();
        final Document document = DocumentUtil.getWebsiteDocument(this, TEST_FILE_PATH);
        final Optional<String> extractedUrl = website.extractWebsiteUrl(document);
        assertTrue(extractedUrl.isPresent(), "Failed to extract website URL.");
        assertEquals("//hn.cz/", extractedUrl.get(),
                "Extracted URL does not match.");
        final Set<ArticleInfo> articleInfos = website.extractArticlesFromDocument(document);
        assertEquals(31, articleInfos.size(), "Did not find expected number of articles.");
    }

}
