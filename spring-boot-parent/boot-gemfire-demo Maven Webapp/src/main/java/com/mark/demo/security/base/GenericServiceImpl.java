package com.mark.demo.security.base;

import java.io.Serializable;

import org.apache.geode.DataSerializable;
import org.springframework.data.gemfire.repository.GemfireRepository;


public abstract class GenericServiceImpl<T extends DataSerializable,I extends Serializable> implements GenericService <T,I> {
    /**
     * 持久层对象
     */
    
    protected GemfireRepository<T,I> dao;

    protected GenericServiceImpl(GemfireRepository<T,I> dao){
    	this.dao=dao;
    }
    

    /**
     * 逻辑删除
     *
     * @param refrencdId
     * @author chenjp
     */
    public void delete(I refrencdId) {
         dao.deleteById(refrencdId);
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
         dao.deleteById(refrenceid);
    }
    
    public Iterable<T> findAll() {
		return dao.findAll();
	}
}
