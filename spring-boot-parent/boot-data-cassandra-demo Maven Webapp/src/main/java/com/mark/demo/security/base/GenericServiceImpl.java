package com.mark.demo.security.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mark.demo.security.entity.Resource;


public abstract class GenericServiceImpl<T extends GenericEntity> implements GenericService <T> {
    /**
     * 持久层对象
     */
    
    protected GenericMapper<T> dao;

    protected GenericServiceImpl(GenericMapper<T> dao){
    	this.dao=dao;
    }
    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 逻辑删除
     *
     * @param refrencdId
     * @author chenjp
     */
    public int delete(String refrencdId) {
        return dao.delete(refrencdId);
    }

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(T entity) {
        return dao.insert(entity);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param refrenceid
     * @return
     * @see public int delete(T entity)
     */
    public  int deleteByPrimaryKey(String refrenceid) {
        return dao.deleteByPrimaryKey(refrenceid);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public PaginateResult<T> findPage(Pagination page, T entity) {
        entity.setPagination(page);
        PaginateResult<T> pageResult = new PaginateResult<T>(page, dao.findList(entity));
        pageResult.setTotal(page.getTotalCount());
        return pageResult;
    }
    
    public List<T> findAll() {
		return dao.selectAll();
	}
}
