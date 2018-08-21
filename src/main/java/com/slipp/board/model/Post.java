package com.slipp.board.model;

import org.joda.time.DateTime;

public class Post {
    private long id;
    private String userId;
    private String title;
    private String content;
    private DateTime createDtm;
    private DateTime updateDtm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public DateTime getCreateDtm() {
        return createDtm;
    }

    public void setCreateDtm(DateTime createDtm) {
        this.createDtm = createDtm;
    }

    public DateTime getUpdateDtm() {
        return updateDtm;
    }

    public void setUpdateDtm(DateTime updateDtm) {
        this.updateDtm = updateDtm;
    }
}
