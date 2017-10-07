package com.mark.demo.security.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mark.demo.security.entity.Resource;


public abstract class GenericServiceImpl<T extends GenericEntity,I extends Serializable> implements GenericService <T,I> {
    /**
     * 持久层对象
     */
    
    protected PagingAndSortingRepository<T,I> dao;

    protected GenericServiceImpl(PagingAndSortingRepository<T,I> dao){
    	this.dao=dao;
    }
    

    /**
     * 逻辑删除
     *
     * @param refrencdId
     * @author chenjp
     */
    public void delete(I refrencdId) {
         dao.delete(refrencdId);
    }

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public T insert(T entity) {
        return dao.save(entity);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param refrenceid
     * @return
     * @see public int delete(T entity)
     */
    public  void deleteByPrimaryKey(I refrenceid) {
         dao.delete(refrenceid);
    }

    
    
    public Iterable<T> findAll() {
		return dao.findAll();
	}
}
