package com.john.www.abu.DatabaseStuffs.sqlUsers;

/**
 * Created by MR AGUDA on 02-Apr-18.
 */

public class UserPostDetails {


    private  String name, userid, image;

    public UserPostDetails() {
    }

    public UserPostDetails(String name, String userid, String image) {
        this.name = name;
        this.userid = userid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
