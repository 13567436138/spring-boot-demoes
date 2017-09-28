package com.mark.demo.security.base;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface GenericDao<T extends GenericEntity>
{
	 /*保存一个对象*/  
    public void save(T t);  
      
    /*根据对象Id查找该对象*/  
    public T queryById(String id);  
      
    /*根据条件查询集合*/  
    public List<T> queryList(Query query);  
      
    /*通过条件查询单个实体*/  
    public T queryOne(Query query);  
      
    /*分页查询*/  
    public List<T> getPage(Query query, int start, int size);  
      
    /*查询符合条件的记录总数*/  
    public Long getPageCount(Query query);  
      
    /*根据id删除对象*/  
    public int deleteById(String id);  
      
    /*删除对象*/  
    public int delete(T t);  
      
    /*更新指定id的属性值*/  
    public int updateFirst(String id, String objName, String objValue);  
      
    /*查找更新*/  
    public int updateInser(Query query, Update update); 
    
    
    public int update(String id,Map<String,Object> updater);
}
