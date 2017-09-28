package com.mark.demo.security.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/*
*hxp(hxpwangyi@126.com)
*2017年9月27日
*
*/
public class GenericDaoImpl<T extends GenericEntity> implements GenericDao<T> {
	private Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);  
    
    @Autowired  
    public MongoTemplate mongoTemplate;  
    
    private Class<T> entityClass;  
    
    public GenericDaoImpl(){
    	Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClass = (Class) params[0];  
    }
  
    /**@Description 
     * 保存一个对象 
     */  
    @Override  
    public void save(T t){  
        this.mongoTemplate.save(t);  
    }  
    /**@Description 
     * 查找对应id的对象,id对应Collection中记录的_id字段 
     */  
    public T queryById(String id) {  
        Query query = new Query();  
        Criteria criteria = Criteria.where("_id").is(id);  
        query.addCriteria(criteria);  
        logger.info(entityClass+"[Mongo]queryById:" + query);  
        return this.mongoTemplate.findOne(query, entityClass);  
    }  
    /**@Description 
     * 根据条件查询集合 
     */  
    public List<T> queryList(Query query){  
        logger.info(entityClass+"[Mongo]queryList:" + query);  
        return this.mongoTemplate.find(query, entityClass);  
    }  
    /**@Description 
     * 通过条件查询单个实体 
     * 查询单个用的是mongoTemplate.findOne方法,查询多条的用的是mongoTemplate.find 
     */  
    public T queryOne(Query query){  
        logger.info(entityClass+"[Mongo] queryOne:" + query);  
        return this.mongoTemplate.findOne(query, entityClass);  
    }  
    /**@Description 
     * 通过条件进行分页查询 
     * 类似mysql查询中的limit 
     */  
    public List<T> getPage(Query query, int start, int size){  
        query.skip(start);  
        query.limit(size);  
        List<T> lists = this.mongoTemplate.find(query, entityClass);  
        logger.info(entityClass+"[Mongo] queryPage:" + query + "(" + start +"," + size +")");  
        return lists;  
    }  
    /**@Description 
     * 根据条件查询库中符合记录的总数,为分页查询服务 
     */  
    public Long getPageCount(Query query){  
        logger.info(entityClass+"[Mongo]queryPageCount:" + query);  
        return this.mongoTemplate.count(query, entityClass);  
    }  
      
    /**@Description 
     * 根据id删除对象 
     */  
    public int deleteById(String id){  
        Criteria criteria = Criteria.where("_id").in(id);   
        Query query = new Query(criteria);  
        int affect=this.mongoTemplate.remove(query).getN();  
        logger.info(entityClass+"[Mongo]deleteById:" + query); 
        return affect;
    }  
    /**@Description 
     * 删除对象 
     */  
    public int delete(T t){ 
    	logger.info("[Mongo]delete:" + t); 
        return this.mongoTemplate.remove(t).getN();  
         
    }  
    /**@Description 
     * 更新指定id的属性值 
     */  
    public int updateFirst(String id, String objName, String objValue){  
        Criteria criteria = Criteria.where("_id").in(id);    
        Query query = new Query(criteria);  
        Update update=new Update();  
        update.set(objName,objValue);  
        int affect=this.mongoTemplate.updateFirst(query,update,entityClass).getN();
        logger.info(entityClass+"[Mongo]updateFirst:query(" + query + "),update(" + update + ")");  
        return affect;
    }  
    /** 
     *  Created on 2017年3月27日  
     * <p>Discription:[查找更新,如果没有找到符合的记录,则将更新的记录插入库中]</p> 
     */  
    public int updateInser(Query query, Update update){  
        logger.info(entityClass+"[Mongo]updateInser:query(" + query + "),update(" + update + ")");  
        return this.mongoTemplate.upsert(query, update, entityClass).getN();  
    }

	@Override
	public int update(String id, Map<String, Object> updater) {
		Criteria criteria = Criteria.where("_id").in(id); 
		Query query = new Query(criteria);  
		Update update=new Update();  
		Iterator<Entry<String,Object>> iterator=updater.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,Object> entry=iterator.next();
			update.set(entry.getKey(),entry.getValue()); 
		}
         
        int affect=this.mongoTemplate.updateFirst(query,update,entityClass).getN();
        return affect;
	}  
  

}
