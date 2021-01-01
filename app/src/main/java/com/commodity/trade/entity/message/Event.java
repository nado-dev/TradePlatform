package com.commodity.trade.entity.message;

/**
 * @author Aaron
 */
public class Event {

    public String messageString;
    public int messageInt;


    public Event(String message) {
        this.messageString = message;
    }
    public Event(int message) {
        this.messageInt = message;
    }

    public String getMessageString() {
        return messageString;
    }

    public int getMessageInt() {
        return messageInt;
    }
}