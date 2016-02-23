package com.reficul.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuzl on 16-2-19.
 */
public class Category implements Serializable {
    public String Id;
    public String Category;
    public long TopicCounts;

    public String getCategory() {
        return Category;
    }

    public String getId() {
        return Id;
    }

    public long getTopicCounts() {
        return TopicCounts;
    }
}
