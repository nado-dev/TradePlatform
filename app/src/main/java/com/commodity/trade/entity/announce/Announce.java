package com.commodity.trade.entity.announce;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 通知实体类
 * @author Aaron
 */
public class Announce extends BmobObject implements Serializable {

    private int announceId;
    private String title;
    private String content;
    private long sendDate;

    @Override
    public String toString() {
        return "Announce{" +
                "announceId=" + announceId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", status=" + status +
                '}';
    }

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Announce() {
    }

    public Announce(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(int announceId) {
        this.announceId = announceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public Announce(int announceId, String title, String content, long sendDate) {
        this.announceId = announceId;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
    }

    public String translateTimeStampToString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(sendDate));
    }
}
