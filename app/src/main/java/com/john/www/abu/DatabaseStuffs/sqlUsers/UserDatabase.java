package com.john.www.abu.DatabaseStuffs.sqlUsers;

import java.io.Serializable;

public class UserDatabase implements Serializable{

    private  String name, userid, status;
    private  String online;
    private String image;
    private String thumbIamgeUri,Department;

    private byte[] imagebyte;

    public UserDatabase() {
    }

    public UserDatabase(String name, String status, String image, String userid) {
        this.name = name;
        this.userid = userid;
        this.status = status;
        this.image = image;

    }
    public UserDatabase(String name, String status, String userid) {
        this.name = name;
        this.userid = userid;
        this.status = status;


    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getThumbIamgeUri() {
        return thumbIamgeUri;
    }

    public void setThumbIamgeUri(String thumbIamgeUri) {
        this.thumbIamgeUri = thumbIamgeUri;
    }

    public byte[] getImagebyte() {
        return imagebyte;
    }

    public void setImagebyte(byte[] imagebyte) {
        this.imagebyte = imagebyte;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
