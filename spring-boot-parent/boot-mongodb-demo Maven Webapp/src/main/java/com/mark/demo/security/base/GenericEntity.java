package com.mark.demo.security.base;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public abstract class GenericEntity implements Serializable {

    private static final long serialVersionUID = -5070399515553795258L;

    /**
     * 主键编号
     */
    @Id
    protected String id;

    /**
     * 删除标识
     */
    protected Boolean delFlag = false;

    /**
     * 分页对象
     * <p>用于动态加入SQL分页语句的对象</p>
     */
    protected Pagination pagination;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }




	public String getId() {
		return id;
	}




	public void setId(String id) {
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
