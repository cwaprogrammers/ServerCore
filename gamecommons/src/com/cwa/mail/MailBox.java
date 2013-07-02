package com.cwa.mail;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MailBox<T extends Mail> {
    
    private int capacity; // 最多能装多少封邮件
    private int newMailId = 1; // 自增ID，从1开始
    private LinkedList<T> mails;

    public MailBox() {
        
    }

    public MailBox(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNewMailId() {
        return newMailId;
    }
    public void setNewMailId(int newMailId) {
        this.newMailId = newMailId;
    }

    public List<T> getMails() {
        return mails;
    }
    public void setMails(List<T> mails) {
        this.mails = new LinkedList<T>(mails);
    }
    
    // 添加新邮件
    public void addNewMail(T mail) {
        if (capacity > 0 && mails.size() >= capacity) {
            // 邮箱满了，把最老的一封邮件删除
            mails.pollFirst();
        }
        
        mail.setId(newMailId++);
        mail.setSentTime(new Date());
        mails.add(mail);
    }
    
    // 删除ID<=mailId的老邮件
    public void deleteMailsOlderThan(int mailId) {
        for (Iterator<T> it = mails.iterator(); it.hasNext();) {
            T mail = it.next();
            if (mail.getId() <= mailId) {
                it.remove();
            }
        }
    }

    @Override
    public String toString() {
        return "MailBox{mails=" + (mails == null ? 0 : mails.size()) + '}';
    }
    
}
