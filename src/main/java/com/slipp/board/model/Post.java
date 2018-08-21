package com.slipp.board.model;

import org.joda.time.DateTime;

public class Post {
    private long id;
    private String userId;
    private String title;
    private String content;
    private DateTime createDtm;
    private DateTime updateDtm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (userId != null ? !userId.equals(post.userId) : post.userId != null) return false;
        if (title != null ? !title.equals(post.title) : post.title != null) return false;
        if (content != null ? !content.equals(post.content) : post.content != null) return false;
        if (createDtm != null ? !createDtm.equals(post.createDtm) : post.createDtm != null) return false;
        return updateDtm != null ? updateDtm.equals(post.updateDtm) : post.updateDtm == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createDtm != null ? createDtm.hashCode() : 0);
        result = 31 * result + (updateDtm != null ? updateDtm.hashCode() : 0);
        return result;
    }

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
