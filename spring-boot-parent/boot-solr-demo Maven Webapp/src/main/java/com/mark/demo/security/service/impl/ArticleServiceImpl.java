package com.mark.demo.security.service.impl;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.Article;
import com.mark.demo.security.mapper.ArticleMapper;
import com.mark.demo.security.repsitory.SolrRepsitory;
import com.mark.demo.security.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private SolrRepsitory solrRepsitory;

    @Autowired(required = true)
    public ArticleServiceImpl( ArticleMapper dao){
        super(dao);
        this.articleMapper=dao;
    }

    public int insert(Article article){
        int ret=super.insert(article);
        solrRepsitory.save(article);
        return ret;
    }

    @Override
    public Page<Article> findByTitleOrContent(String title, String content, Pageable pageable) {
        return solrRepsitory.findByTitleLikeOrContentLike(title,content,pageable);
    }
}
