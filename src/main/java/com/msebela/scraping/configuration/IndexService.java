package com.msebela.scraping.configuration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Slf4j
public class IndexService {

    private final FullTextEntityManager fullTextEntityManager;

    public IndexService(final EntityManagerFactory entityManagerFactory) {
        fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    }
    @Transactional
    public void indexEntities() {
        log.info("Starting indexing entities.");
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Indexing finished.");
    }

}
