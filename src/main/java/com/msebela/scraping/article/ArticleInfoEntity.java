package com.msebela.scraping.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;

@Entity
@Table(name = "articles",
        indexes = {@Index(name = "index_url", columnList = "url", unique = true)})
@Indexed
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfoEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * URL of article.
     */
    @Column(unique = true)
    private String url;

    /**
     * Headline of article.
     */
    @FullTextField
    private String headline;
}
