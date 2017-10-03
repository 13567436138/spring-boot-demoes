package com.mark.demo.security.controller;

import com.mark.demo.security.base.PaginateResult;
import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.constant.CommonConst;
import com.mark.demo.security.entity.Article;
import com.mark.demo.security.entity.JsonMessage;
import com.mark.demo.security.service.ArticleService;
import com.mark.demo.security.utils.JsonMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by admin on 2017/9/30.
 */
@Controller
@RequestMapping("/admins/search/solr")
public class SolrController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/list")
    public String list(HttpServletResponse response){
        response.setHeader("X-Frame-Options","SAMEORIGHT");
        return "admins/search/solr/list";
    }

    @RequestMapping("/list/data")
    @ResponseBody
    public PaginateResult<Article> listData(String key, Pagination pagination){
        Pageable pageable=new PageRequest(pagination.getCurrentPage()-1,pagination.getPageSize());
        Page<Article> ret=articleService.findByTitleOrContent(key,key,pageable);
        PaginateResult<Article> pageResult = new PaginateResult<Article>(pagination, ret.getContent());
        pageResult.setTotal(ret.getTotalElements());
        return pageResult;
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonMessage add(Article article){
        article.setDate(new Date());
        articleService.insert(article);
        return JsonMessageUtils.getJsonMessage(CommonConst.SUCCESS);
    }

}
