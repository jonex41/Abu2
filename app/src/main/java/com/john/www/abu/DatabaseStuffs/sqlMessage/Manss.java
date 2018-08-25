package com.john.www.abu.DatabaseStuffs.sqlMessage;

import java.io.Serializable;

/**
 * Created by MR AGUDA on 14-Mar-18.
 */

public class Manss implements Serializable {

    private String message, senderId, recieverId, type, firstkey, secondkey, send_audio;
    private byte[] sendImage;
    private String time;
    private boolean seen;



    public Manss() {
    }

    public Manss(byte[] sendImage, String senderId, String recieverId, String type,String time, boolean seen) {
        this.sendImage = sendImage;
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.type = type;
        this.time = time;
        this.seen = seen;
    }
    public Manss(String send_audio, String senderId, String recieverId, String type,String time, boolean seen) {
        this.send_audio = send_audio;
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.type = type;
        this.time = time;
        this.seen = seen;
    }

    public Manss(String senderId, String type, byte[] sendImage, String time) {
        this.senderId = senderId;
        this.type = type;

        this.sendImage = sendImage;
        this.time = time;
    }

    public Manss(String message, String senderId, String type, String time) {
        this.message = message;
        this.senderId = senderId;
        this.type = type;
        this.time = time;

    }

    public Manss(String message, String time, String senderId, String recieverId, String type) {
        this.message = message;
        this.time = time;
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.type = type;
    }

    public String getSend_audio() {
        return send_audio;
    }

    public void setSend_audio(String send_audio) {
        this.send_audio = send_audio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public byte[] getSendImage() {
        return sendImage;
    }

    public void setSendImage(byte[] sendImage) {
        this.sendImage = sendImage;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstkey() {
        return firstkey;
    }

    public void setFirstkey(String twokeys) {
        this.firstkey = twokeys;
    }

    public String getSecondkey() {
        return secondkey;
    }

    public void setSecondkey(String secondkey) {
        this.secondkey = secondkey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
