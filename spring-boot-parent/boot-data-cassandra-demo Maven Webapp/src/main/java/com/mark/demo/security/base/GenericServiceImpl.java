package com.mark.demo.security.base;

import java.io.Serializable;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.support.BasicMapId;


public abstract class GenericServiceImpl<T extends GenericEntity,I extends Serializable> implements GenericService <T,I> {
    /**
     * 持久层对象
     */
    
    protected CassandraRepository<T> dao;

    protected GenericServiceImpl(CassandraRepository<T> dao){
    	this.dao=dao;
    }
    

    /**
     * 逻辑删除
     *
     * @param refrencdId
     * @author chenjp
     */
    public void delete(I refrencdId) {
         dao.delete(BasicMapId.id("id", refrencdId));
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
         dao.delete(BasicMapId.id("id", refrenceid));
    }

    
    
    public Iterable<T> findAll() {
		return dao.findAll();
	}
}
