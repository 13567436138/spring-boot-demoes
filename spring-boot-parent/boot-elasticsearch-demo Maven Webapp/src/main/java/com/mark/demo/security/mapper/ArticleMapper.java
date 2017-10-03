package com.mark.demo.security.mapper;

import com.mark.demo.security.anno.MyBatisDao;
import com.mark.demo.security.base.GenericMapper;
import com.mark.demo.security.entity.Article;

/**
 * Created by admin on 2017/10/1.
 */
@MyBatisDao
public interface ArticleMapper extends GenericMapper<Article>{

}
