package com.msebela.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DocumentUtil {

    /**
     * Obtains document from file.
     * @param filepath Path to the file.
     * @return Obtained document.
     */
    public static Document getWebsiteDocument(Object context, final String filepath) {
        final File website = new File(context.getClass().getResource(filepath).getFile());
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
