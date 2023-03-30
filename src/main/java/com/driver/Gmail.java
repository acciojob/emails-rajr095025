package com.driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Gmail extends Email {

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //HashMap<String,Mail> inbox = new HashMap<>();
    Deque <Mail> inbox = new ArrayDeque<>();
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    HashMap<String,Mail> trash = new HashMap<>();

    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
    }

    public void receiveMail(Date date, String sender, String message) throws ParseException {
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        Mail mail = new Mail(date,sender,message);
        if(inbox.size() < inboxCapacity){
            inbox.offerLast(mail);
            return;
        }
        String oldestMessage = findOldestMessage();
        deleteMail(oldestMessage);
        inbox.offerLast(mail);
        /*
        Mail oldMail = inbox.get(oldestMessage);
        trash.put(oldestMessage, oldMail);
        inbox.remove(oldestMessage);
        */
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        int size = inbox.size();
        Mail deletedMail = null;
        for(int i=0; i<size; i++){
            Mail temp = inbox.poll();
            if(temp.getMessage().equals(message)){
                deletedMail = temp;
                continue;
            }
            inbox.offerLast(temp);
        }
        if(deletedMail == null) return;
        trash.put(message,deletedMail);
    }

    public String findLatestMessage() throws ParseException {
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inbox.isEmpty()){
            return null;
        }
        return inbox.peekLast().getMessage();
        /*
        String latestMessage = "";
        Date latestDate = new SimpleDateFormat("dd/MM/yyyy").parse("03/07/1000");
        for(Mail mail : inbox.values()){
            if(latestDate.compareTo(mail.getDate()) < 0){
                latestMessage = mail.getMessage();
                latestDate = mail.getDate();
            }
        }
        return latestMessage;
        */
    }

    public String findOldestMessage() throws ParseException {
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.isEmpty()){
            return null;
        }
        return inbox.peekFirst().getMessage();
        /*
        String oldMessage = "";
        Date oldDate = new SimpleDateFormat("dd/MM/yyyy").parse("03/07/9999");
        for(Mail mail : inbox.values()){
            if(oldDate.compareTo(mail.getDate()) > 0){
                oldMessage = mail.getMessage();
                oldDate = mail.getDate();
            }
        }
        return oldMessage;
        */

    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count = 0;
        int size = inbox.size();
        for(int i=0; i<size; i++){
            Mail temp = inbox.poll();
            if(temp.getDate().compareTo(start) >= 0 && temp.getDate().compareTo(end) <= 0){
                count++;
            }
            inbox.add(temp);
        }
        return count;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inbox.size();
    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        trash.clear();
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}
