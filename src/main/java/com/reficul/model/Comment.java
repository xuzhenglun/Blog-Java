package com.reficul.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by xuzl on 16-2-19.
 */
public class Comment extends User implements Serializable {
    public long Id;
    public long Tid;
    public long Uid;
    public String Content;
    public Timestamp Created;
    public Timestamp Updated;

    public String getContent() {
        return Content;
    }

    public Timestamp getCreated() {
        return Created;
    }

    public long getId() {
        return Id;
    }

    public long getTid() {
        return Tid;
    }

    public long getUid() {
        return Uid;
    }

    public Timestamp getUpdated() {
        return Updated;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setCreated(Timestamp created) {
        Created = created;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setTid(long tid) {
        Tid = tid;
    }

    public void setUid(long uid) {
        Uid = uid;
    }

    public void setUpdated(Timestamp updated) {
        Updated = updated;
    }
}
