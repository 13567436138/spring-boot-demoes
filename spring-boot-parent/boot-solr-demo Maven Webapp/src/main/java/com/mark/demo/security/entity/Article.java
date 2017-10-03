package com.mark.demo.security.entity;

import com.mark.demo.security.base.GenericEntity;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

/**
 * Created by admin on 2017/9/30.
 */
@SolrDocument(solrCoreName = "test")
public class Article extends GenericEntity{
    @Indexed
    @Field("title")
    private String title;
    @Indexed
    @Field("content")
    private String content;
    @Indexed
    @Field("date")
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
