package com.msebela.scraping;

import com.msebela.scraping.article.ArticleInfoEntity;
import com.msebela.scraping.article.ArticleInfoRepository;
import com.msebela.scraping.configuration.ApplicationProperties;
import com.msebela.scraping.scraper.WebScraper;
import com.msebela.scraping.scraper.WebScraperService;
import com.msebela.scraping.scraper.websites.WebsiteInfo;
import com.msebela.scraping.scraper.websites.WebsiteType;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticlesApplicationIntegrationTests {

	private static final String TEST_FILE_PATH = "/websites/idnes_website_short.html";

	@Mock
	private Connection mockConnection;

	@Mock
	private ApplicationProperties applicationProperties;

	@InjectMocks
	private WebScraper webScraper;

	@Mock
	private ArticleInfoRepository repository;

	private WebScraperService webScraperService;

	@Test
	void testArticlesAreStored() {
		webScraperService = new WebScraperService(repository, webScraper);

		final String url = "http://example.com/";
		final Document document = DocumentUtil.getWebsiteDocument(this, TEST_FILE_PATH);
		Set<WebsiteInfo> websiteInfo = null;
		try {
			websiteInfo = Set.of(new WebsiteInfo(new URL(url), WebsiteType.IDNES));
		} catch (MalformedURLException e) {
			fail(e);
		}
		final Set<String> testUrls =
				Set.of("https://example.com/url1", "https://example.com/url2", "https://example.com/url3");
		final List<ArticleInfoEntity> repositoryReturn =
				List.of(new ArticleInfoEntity(null, "https://example.com/url1", "Headline 1"));
		final Set<ArticleInfoEntity> repositorySave =
				Set.of(new ArticleInfoEntity(null, "https://example.com/url2", "Headline 2"),
						new ArticleInfoEntity(null, "https://example.com/url3", "Headline 3"));
		when(applicationProperties.getConnectTimeoutSeconds()).thenReturn(1);
		when(mockConnection.timeout(1000)).thenReturn(mockConnection);
		try {
			when(mockConnection.get()).thenReturn(document);
		} catch (IOException e) {
			fail(e);
		}
		when(repository.findAllByUrlIn(testUrls)).thenReturn(repositoryReturn);

		try (MockedStatic<Jsoup> mockJsoup = Mockito.mockStatic(Jsoup.class)) {
			mockJsoup.when(() -> Jsoup.connect(url)).thenReturn(mockConnection);

			webScraperService.scrapeWebsAndSaveResults(websiteInfo);
			verify(repository, times(1)).saveAll(repositorySave);
		}
	}
}
