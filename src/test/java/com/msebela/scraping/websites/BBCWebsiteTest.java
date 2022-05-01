package com.msebela.scraping.websites;

import com.msebela.scraping.article.dto.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BBCWebsiteTest {

    private static final String TEST_FILE_NAME = "/websites/bbc_website.html";

    @Test
    public void testExtractArticlesFromDocument() {
        final BBCWebsite website = new BBCWebsite();
        final Document document = getWebsiteDocument();
        final Optional<String> extractedUrl = website.extractWebsiteUrl(document);
        assertTrue(extractedUrl.isPresent(), "Failed to extract website URL.");
        assertEquals("https://www.bbc.com/", extractedUrl.get(),
                "Extracted URL does not match.");
        final Set<ArticleInfo> articleInfos = website.extractArticlesFromDocument(document);
        assertEquals(42, articleInfos.size(), "Did not find expected number of articles.");
    }

    private Document getWebsiteDocument() {
        final File website = new File(getClass().getResource(TEST_FILE_NAME).getFile());
        assertNotNull(website, "Test file not found.");
        try {
            final Document document = Jsoup.parse(website, "UTF-8", "");
            assertNotNull(document, "Document parsing failed.");
            return document;
        } catch (IOException e) {
            fail("Document parsing failed.", e);
        }
        return null;
    }
}
