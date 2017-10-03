package com.mark.demo.security.base;

import java.util.List;

public interface GenericMapper<T extends GenericEntity>
{
    /**
     * 插入数据
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 逻辑删除
     * @param refrencdId
     *  @author chenjp
     * @return
     */
    int delete(String refrencdId);

    /**
     * 删除数据(物流删除）
     * @param refrenceid
     * @see public int delete(T entity)
     * @return
     */
    int deleteByPrimaryKey(String refrenceid);

    /**
     * 根据条件是否插入数据
     * @param record
     */
    void insertSelective(T record);

    /**
     * 根据主键查询数据
     * @param refrenceid
     * @return
     */
    T selectByPrimaryKey(String refrenceid);

    /**
     * 选择性更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键更新一条信息所有数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<T> findList(T entity);
    
    /**
     * 获取记录总数
     * @param entity
     * @return
     */
    public Long count(T entity);

    /**
     * 查询所有数据列表
     * @return
     */
    public List<T> selectAll();
}
