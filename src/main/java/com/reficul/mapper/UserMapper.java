package com.reficul.mapper;

import com.reficul.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xuzl on 16-2-19.
 */
public interface UserMapper {
    @Select("SELECT * FROM User WHERE Id = #{Id}")
    User findUserById(@Param("Id") long Id);

    @Select("SELECT * FROM User WHERE Name = #{Name}")
    User findUserByName(@Param("Name") String Name);
}
