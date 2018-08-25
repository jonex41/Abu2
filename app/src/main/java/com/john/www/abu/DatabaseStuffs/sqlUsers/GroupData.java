package com.john.www.abu.DatabaseStuffs.sqlUsers;

import java.io.Serializable;

public class GroupData implements Serializable {


    public GroupData() {
    }

    private String group_name, group_Image_realURL,group_Image_fakeURL ;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_Image_realURL() {
        return group_Image_realURL;
    }

    public void setGroup_Image_realURL(String group_Image_realURL) {
        this.group_Image_realURL = group_Image_realURL;
    }

    public String getGroup_Image_fakeURL() {
        return group_Image_fakeURL;
    }

    public void setGroup_Image_fakeURL(String group_Image_fakeURL) {
        this.group_Image_fakeURL = group_Image_fakeURL;
    }
}
