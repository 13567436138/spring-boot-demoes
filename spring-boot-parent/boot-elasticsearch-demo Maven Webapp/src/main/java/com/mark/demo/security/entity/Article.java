package com.mark.demo.security.entity;

import com.mark.demo.security.base.GenericEntity;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by admin on 2017/9/30.
 */
@Document(indexName = "idx_article2",type="article",createIndex = true)
public class Article extends GenericEntity{
    @Field(type= FieldType.String,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type= FieldType.String,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;
    @Field(type=FieldType.Date)
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
