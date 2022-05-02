package com.msebela.scraping;

import com.msebela.scraping.article.ArticleSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ArticlesApplicationTests {

	@MockBean
	private ArticleSearchService articleSearchService;

	@Test
	void contextLoads() {
	}

}
