package com.john.www.abu.DatabaseStuffs.sqlUsers;

import java.io.Serializable;

public class UserForPost implements Serializable {

    private  String name, userid, Department;
   private String image;

    public UserForPost() {
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
