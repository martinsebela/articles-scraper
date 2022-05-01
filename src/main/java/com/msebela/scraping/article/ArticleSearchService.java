package com.msebela.scraping.article;

import com.msebela.scraping.article.dto.ArticleInfo;
import com.msebela.scraping.article.dto.ArticlesResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class ArticleSearchService {

    private static final String HEADLINE_FIELD_NAME = "headline";

    private final EntityManager entityManager;

    /**
     * Initial full-text indexing operation.
     */
    @Transactional
    public void indexEntities() {
        log.info("Starting indexing entities.");
        final SearchSession searchSession = Search.session(entityManager);
        final MassIndexer indexer = searchSession.massIndexer(ArticleInfoEntity.class);
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Indexing finished.");
    }

    public ArticlesResult searchArticlesByKeywords(final List<String> keywords) {
        log.info("Searching for articles with keywords {}.", keywords);
        final SearchSession searchSession = Search.session(entityManager);
        Set<ArticleInfoEntity> foundArticles = new HashSet<>();
        for (String keyword : keywords) {
            foundArticles.addAll(performSearchByKeyword(searchSession, keyword).hits());
        }
        log.info("Found {} articles.", foundArticles.size());
        return new ArticlesResult(
                foundArticles.stream().map(a -> new ArticleInfo(a.getUrl(), a.getHeadline())).toList());
    }

    private SearchResult<ArticleInfoEntity> performSearchByKeyword(
            final SearchSession searchSession, final String keyword) {
        return searchSession.search(ArticleInfoEntity.class)
                .where(f -> f.match().field(HEADLINE_FIELD_NAME).matching(keyword)).fetchAll();
    }

}
