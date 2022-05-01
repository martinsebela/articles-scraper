package com.msebela.scraping.configuration;

import com.msebela.scraping.article.ArticleInfo;
import com.msebela.scraping.article.ArticleInfoEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ArticleSearchService {

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

    public List<ArticleInfo> searchArticlesByKeywords(final List<String> keywords) {
        log.info("Searching for articles with keywords {}.", keywords);
        final SearchSession searchSession = Search.session(entityManager);
        final SearchResult<ArticleInfoEntity> results = searchSession.search(ArticleInfoEntity.class)
                .where(f -> f.match().field("headline").matching("byla")).fetchAll();
        log.info("Found {} articles.", results.hits());
        return results.hits().stream().map(a -> new ArticleInfo(a.getUrl(), a.getHeadline())).toList();
    }

}
