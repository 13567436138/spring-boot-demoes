package com.mark.demo.security.entity;

import org.springframework.data.cassandra.mapping.Table;

import com.mark.demo.security.base.GenericEntity;

@Table
public class Permission extends GenericEntity{

    private String permission;

    private String desc;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}