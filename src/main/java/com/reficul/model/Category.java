package com.reficul.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuzl on 16-2-19.
 */
public class Category implements Serializable {
    public String Id;
    public String Category;
    public Date Created;
    public long Views;
    public Date TopicTime;
    public long TopicCounts;
    public long TopicLastUserId;

    public Date getCreated() {
        return Created;
    }

    public String getId() {
        return Id;
    }

    public String getCategory() {
        return Category;
    }

    public long getTopicCounts() {
        return TopicCounts;
    }

    public long getTopicLastUserId() {
        return TopicLastUserId;
    }

    public Date getTopicTime() {
        return TopicTime;
    }

    public long getViews() {
        return Views;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setTopicCounts(long topicCounts) {
        TopicCounts = topicCounts;
    }

    public void setTopicLastUserId(long topicLastUserId) {
        TopicLastUserId = topicLastUserId;
    }

    public void setTopicTime(Date topicTime) {
        TopicTime = topicTime;
    }

    public void setViews(long views) {
        Views = views;
    }
}
