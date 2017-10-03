package com.mark.demo.security.repsitory;

import com.mark.demo.security.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Created by admin on 2017/10/2.
 */
public interface ArticleRepsitory extends ElasticsearchCrudRepository<Article,Integer>{
    Page<Article> findByTitleLikeOrContentLike(String title,String content,Pageable pageable);
}

