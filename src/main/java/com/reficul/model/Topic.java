package com.reficul.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by xuzl on 16-2-19.
 */
public class Topic implements Serializable {
    public long Id;
    public long Uid;
    public String Title;
    public String Category;
    public String Tag;
    public String Content;
    public String Attachment;
    public Timestamp Created;
    public Timestamp Updated;
    public long Views;
    public long ReplyCounts;
    public long ReplyLastUserId;
    public Timestamp LastReply;

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }


    public void setCategory(String category) {
        Category = category;
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

    public void setLastReply(Timestamp lastReply) {
        LastReply = lastReply;
    }

    public void setReplyCounts(long replyCounts) {
        ReplyCounts = replyCounts;
    }

    public void setReplyLastUserId(long replyLastUserId) {
        ReplyLastUserId = replyLastUserId;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setUid(long uid) {
        Uid = uid;
    }

    public void setUpdated(Timestamp updated) {
        Updated = updated;
    }

    public void setViews(long views) {
        Views = views;
    }

    public String getAttachment() {

        return Attachment;
    }

    public String getCategory() {
        return Category;
    }

    public String getContent() {
        return Content;
    }

    public Timestamp getCreated() {
        return Created;
    }

    public long getId() {
        return Id;
    }

    public Timestamp getLastReply() {
        return LastReply;
    }

    public long getReplyCounts() {
        return ReplyCounts;
    }

    public long getReplyLastUserId() {
        return ReplyLastUserId;
    }

    public String getTag() {
        return Tag;
    }

    public String getTitle() {
        return Title;
    }

    public long getUid() {
        return Uid;
    }

    public Timestamp getUpdated() {
        return Updated;
    }

    public long getViews() {
        return Views;
    }
}
