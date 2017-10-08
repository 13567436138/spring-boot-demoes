package com.mark.demo.security.base;

import org.apache.geode.DataSerializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


public abstract class GenericEntity implements DataSerializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5070399515553795258L;

	/**
     * 主键编号
     */
    @Id
    protected int id;

    /**
     * 删除标识
     */
    protected Boolean delFlag = false;

    /**
     * 分页对象
     * <p>用于动态加入SQL分页语句的对象</p>
     */
    @Transient
    protected Pagination pagination;

   


    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }


	public Pagination getPagination() {
		return pagination;
	}


	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

   
}
