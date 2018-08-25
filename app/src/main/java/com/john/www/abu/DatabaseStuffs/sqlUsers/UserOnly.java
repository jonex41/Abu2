package com.john.www.abu.DatabaseStuffs.sqlUsers;

import java.io.Serializable;

public class UserOnly implements Serializable {
    private  String name, status,image;

    public UserOnly() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
