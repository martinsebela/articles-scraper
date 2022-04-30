package com.msebela.scraping.websites;

import com.msebela.scraping.article.ArticleInfo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class IdnesWebsite extends Website {

    /**
     * XPath selector for obtaining article elements.
     */
    private final String ARTICLE_XPATH = "//a[contains(@class, 'art-link')]";

    /**
     * XPath selector to get headline text from article element.
     */
    private final String ARTICLE_HEADLINE_XPATH = "//h3/text()";

    /**
     * HTML href attribute containing link.
     */
    private final String LINK_ATTRIBUTE = "href";

    public IdnesWebsite() {
        super(WebsiteType.IDNES);
    }

    @Override
    public List<ArticleInfo> extractArticlesFromDocument(Document document) {
        final Elements elements = document.selectXpath(ARTICLE_XPATH);
        final List<ArticleInfo> foundArticles =
                elements.stream().map(this::extractArticleInfo).
                        filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        return null;
    }

    private Optional<ArticleInfo> extractArticleInfo(final Element element) {
        final String url = element.attributes().get(LINK_ATTRIBUTE);
        if (url == null) {
            log.warn("Cannot obtain link from article element: {}." + element);
            return Optional.empty();
        }
        final Optional<TextNode> headline = element.selectXpath(ARTICLE_HEADLINE_XPATH, TextNode.class).stream().findFirst();
        if (headline.isEmpty()) {
            log.warn("Cannot obtain headline from article element: {}." + element);
            return Optional.empty();
        }
        return Optional.of(new ArticleInfo(url, headline.get().getWholeText()));
    }
}
