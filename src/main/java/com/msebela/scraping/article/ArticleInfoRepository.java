package com.msebela.scraping.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleInfoRepository extends JpaRepository<ArticleInfoEntity, Long> {
    List<ArticleInfoEntity> findAllByUrlIn(Iterable<String> urls);
}
