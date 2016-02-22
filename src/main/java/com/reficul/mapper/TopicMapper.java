package com.reficul.mapper;

import com.reficul.model.Topic;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by xuzl on 16-2-19.
 */
public interface TopicMapper {
    @Select("SELECT * FROM Topic")
    List<Topic> findAllTopics();

    @Select("SELECT * FROM Topic WHERE Id = #{Id}")
    Topic findTopicById(@Param("Id") String Id);

    @Select("SELECT * FROM Topic WHERE Title = #{Title}")
    Topic findTopicByTitle(@Param("Title") String title);

    @Insert("INSERT INTO Topic (Uid,Title,Category,Tag,Content,Attachment) VALUE (#{Uid}," +
            "#{Title},#{Category},#{Tag},#{Content},#{Attachment})")
    void insertTopic(@Param("Uid") long Uid,
                     @Param("Title") String Title,
                     @Param("Category") String Category,
                     @Param("Tag") String Tag,
                     @Param("Content") String Content,
                     @Param("Attachment") String Attachment);

    @Update("Update Topic SET Uid = #{Uid}," +
            "Title = #{Title}," +
            "Category = #{Title}," +
            "Tag = #{Tag}," +
            "Content = #{Content}," +
            "Attachment =#{Attachment} " +
            "WHERE Id = #{Id}")
    void updateTopic(@Param("Id") String Id,
                     @Param("Uid") long Uid,
                     @Param("Title") String Title,
                     @Param("Category") String Category,
                     @Param("Tag") String Tag,
                     @Param("Content") String Content,
                     @Param("Attachment") String Attachment);

    @Delete("DELETE FROM Topic WHERE Id = #{Id}")
    void deleteTopic(@Param("Id") String Id);

    @Update("UPDATE Topic SET LastReply = #{now} WHERE Id = #{tid}")
    void modifyLastReply(@Param("tid") String tid, @Param("now") Timestamp now);

    @Update("UPDATE Topic SET ReplyCounts = #{num} WHERE Id = #{tid}")
    void modifyReplyCounts(@Param("tid") String tid, @Param("num") long num);

    @Update("UPDATE Topic SET Views = #{num} WHERE Id = #{tid}")
    void modifyViews(@Param("tid") String tid, @Param("num") long num);

    @Select("SELECT COUNT(*) FROM Topic WHERE Category = #{cate}")
    long findTopicCounts(@Param("cate") String cate);

    @Select("SELECT * FROM Topic WHERE Category = #{cate}")
    List<Topic> findAllTopicsByCategory(@Param("cate") String cate);
}
