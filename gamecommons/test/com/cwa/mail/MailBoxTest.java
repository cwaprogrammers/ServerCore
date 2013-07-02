package com.cwa.mail;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MailBoxTest {
    
    @Test
    public void mailId() {
        MailBox<Mail> box = new MailBox<Mail>();
        box.setMails(new ArrayList<Mail>());
        assertEquals(1, box.getNewMailId());
        
        Mail newMail1 = new Mail();
        box.addNewMail(newMail1);
        assertEquals(2, box.getNewMailId());
        
        Mail newMail2 = new Mail();
        box.addNewMail(newMail2);
        assertEquals(3, box.getNewMailId());
    }
    
    @Test
    public void deleteMail() {
        MailBox<Mail> box = new MailBox<Mail>();
        box.setMails(new ArrayList<Mail>());
        
        // 10封邮件
        for (int i = 0; i < 10; i++) {
            box.addNewMail(new Mail());
        }
        assertEquals(10, box.getMails().size());
        
        box.deleteMailsOlderThan(0);
        assertEquals(10, box.getMails().size());
        
        // 删掉 1 2
        box.deleteMailsOlderThan(2);
        assertEquals(8, box.getMails().size());
        
        // 删掉 3 4 5
        box.deleteMailsOlderThan(5);
        assertEquals(5, box.getMails().size());
        
        // 全部删掉
        box.deleteMailsOlderThan(100);
        assertEquals(0, box.getMails().size());
    }
    
    @Test
    public void maxMailCount() {
        int maxMailCount = 20;
        
        MailBox<Mail> box = new MailBox<Mail>();
        box.setCapacity(maxMailCount);
        box.setMails(new ArrayList<Mail>());
        
        for (int i = 0; i < 100; i++) {
            Mail newMail1 = new Mail();
            box.addNewMail(newMail1);
        }
        
        assertEquals(maxMailCount, box.getMails().size());
        
        int startId = 100 - maxMailCount;
        for (int i = 0; i < box.getMails().size(); i++) {
            assertEquals(startId + i + 1, box.getMails().get(i).getId());
        }
    }
    
}
