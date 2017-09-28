package com.mark.demo.security.base;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;


public abstract class GenericServiceImpl<T extends GenericEntity> implements GenericService <T> {
    /**
     * 持久层对象
     */
    
    protected GenericDao<T> dao;

    protected GenericServiceImpl(GenericDao<T> dao){
    	this.dao=dao;
    }
    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(Query query) {
        return dao.queryList(query);
    }

    /**
     * 逻辑删除
     *
     * @param refrencdId
     * @author chenjp
     */
    public void delete(String refrencdId) {
         dao.deleteById(refrencdId);
    }

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public void insert(T entity) {
         dao.save(entity);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param refrenceid
     * @return
     * @see public int delete(T entity)
     */
    public  void deleteByPrimaryKey(String refrenceid) {
         dao.deleteById(refrenceid);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public PaginateResult<T> findPage(Pagination page,Query query) {
    	long count=dao.getPageCount(query);
    	List<T>list=dao.getPage(query, page.getStartIndex(), page.getPageSize());
        PaginateResult<T> pageResult = new PaginateResult<T>(page, list);
        pageResult.setTotal(count);
        return pageResult;
    }
    
    public List<T> findAll() {
    	Query query=new Query();
		return dao.queryList(query);
	}
}
