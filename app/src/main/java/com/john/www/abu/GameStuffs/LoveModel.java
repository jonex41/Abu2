package com.john.www.abu.GameStuffs;

import java.io.Serializable;

public class LoveModel implements Serializable {
    private String boy_name;
    private String girl_name;
    private String percent_value;
    private String message_love;

    public String getBoy_name() {
        return boy_name;
    }

    public void setBoy_name(String boy_name) {
        this.boy_name = boy_name;
    }

    public String getGirl_name() {
        return girl_name;
    }

    public void setGirl_name(String girl_name) {
        this.girl_name = girl_name;
    }

    public String getPercent_value() {
        return percent_value;
    }

    public void setPercent_value(String percent_value) {
        this.percent_value = percent_value;
    }

    public String getMessage_love() {
        return message_love;
    }

    public void setMessage_love(String message_love) {
        this.message_love = message_love;
    }
}
