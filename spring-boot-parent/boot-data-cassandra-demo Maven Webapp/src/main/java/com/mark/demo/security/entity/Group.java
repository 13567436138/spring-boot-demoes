package com.mark.demo.security.entity;

import org.springframework.data.cassandra.mapping.Table;

import com.mark.demo.security.base.GenericEntity;

@Table
public class Group extends GenericEntity{

    private String groupname;

    private String groupdesc;

   
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }
}