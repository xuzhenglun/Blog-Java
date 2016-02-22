package com.reficul.mapper;

import com.reficul.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xuzl on 16-2-19.
 */
public interface CommentMapper {
    @Select("SELECT * FROM Comment,User WHERE Comment.Uid = User.Id && Tid = #{tid}")
    List<Comment> findAllComments(@Param("tid") String tid);

    @Delete("DELETE FROM Comment WHERE Id = #{Id}")
    void deleteCommentById(@Param("Id") String Id);

    @Insert("INSERT INTO Comment (Content,Tid,Uid) VALUE (#{Content},#{Tid},#{Uid})")
    void insertComment(@Param("Tid") String tid,
                       @Param("Uid") String uid,
                       @Param("Content") String content);

    @Delete("DELETE FROM Comment WHERE Tid = #{tid}")
    void deleteCommentByTid(@Param("tid") String tid);
}