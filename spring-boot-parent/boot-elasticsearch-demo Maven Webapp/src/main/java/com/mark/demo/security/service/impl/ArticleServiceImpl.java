package com.mark.demo.security.service.impl;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.Article;
import com.mark.demo.security.mapper.ArticleMapper;
import com.mark.demo.security.repsitory.ArticleRepsitory;
import com.mark.demo.security.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2017/10/1.
 */
@Service
@Transactional
public class ArticleServiceImpl extends GenericServiceImpl<Article> implements ArticleService {
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepsitory articleRepsitory;
    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired(required = true)
    public ArticleServiceImpl( ArticleMapper dao){
        super(dao);
        this.articleMapper=dao;
    }

    public int insert(Article article){
        int ret=super.insert(article);
        article=articleRepsitory.save(article);
        return ret;
    }

    @Override
    public Page<Article> findByTitleOrContent(String title, String content, Pageable pageable) {
        return articleRepsitory.findByTitleLikeOrContentLike(title,content,pageable);
    }
}
