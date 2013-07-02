package com.cwa.mail;

import java.util.Date;

public class Mail {
    
    private int id;
    private int type;
    private int senderId;
    private Date sentTime; // 发送时间，精确到秒

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public String toString() {
        return "Mail{" + "id=" + id + ", type=" + type
                + ", senderId=" + senderId + ", sentTime=" + sentTime + '}';
    }
    
}
