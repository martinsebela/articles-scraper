package com.msebela.scraping.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleInfoRepository extends JpaRepository<ArticleInfoEntity, Long> {
}
