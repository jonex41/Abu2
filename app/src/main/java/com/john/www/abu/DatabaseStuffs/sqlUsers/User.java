package com.john.www.abu.DatabaseStuffs.sqlUsers;

/**
 * Created by MR AGUDA on 14-Mar-18.
 */

public class User {

  private  String name, userid, status;
  private  String online;
  String image;

    public User() {
    }

    public User(String name, String status, String image, String userid) {
        this.name = name;
        this.userid = userid;
        this.status = status;
        this.image = image;


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
