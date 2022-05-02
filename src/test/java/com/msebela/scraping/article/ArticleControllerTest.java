package com.msebela.scraping.article;

import com.msebela.scraping.article.dto.ArticleInfo;
import com.msebela.scraping.article.dto.ArticlesResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

	private static final String TEST_FILE_PATH = "/websites/idnes_website_short.html";

	@Mock
	private ArticleSearchService articleSearchService;

	@InjectMocks
	private ArticleController articleController;


	@Test
	void testArticlesAreFound() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
		String keyword = "dog";
		String headline = "dog here";

		when(articleSearchService.searchArticlesByKeywords(List.of(keyword)))
				.thenReturn(new ArticlesResult(List.of(new ArticleInfo("url1", headline))));

		final MvcResult mvcResult = mockMvc.perform(post("/articles/find")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"keywords\": [\"" + keyword + "\"]}"))
				.andExpect(status().isOk())
				.andReturn();
		assertTrue(mvcResult.getResponse().getContentAsString().contains(headline),
				"Response does not contain headline");

	}


}
