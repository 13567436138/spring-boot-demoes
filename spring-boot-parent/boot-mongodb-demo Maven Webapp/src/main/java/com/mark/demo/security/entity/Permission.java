package com.mark.demo.security.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "permission") 
public class Permission {
    private Integer permissionid;

    private String permission;

    private String desc;

    public Integer getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Integer permissionid) {
        this.permissionid = permissionid;
    }

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