package com.mark.demo.security.service;

import com.mark.demo.security.base.GenericService;
import com.mark.demo.security.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by admin on 2017/10/1.
 */
public interface ArticleService extends GenericService<Article> {
    Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);
}
