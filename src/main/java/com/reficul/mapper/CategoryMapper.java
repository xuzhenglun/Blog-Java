package com.reficul.mapper;

import com.reficul.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xuzl on 16-2-19.
 */
public interface CategoryMapper {
    @Select("SELECT * FROM Category")
    List<Category> findAllCategories();

    @Insert("INSERT INTO Category (Category) VALUE (#{Title})")
    void insertCategory(@Param("Title") String Title);

    @Insert("INSERT INTO Category (Category,TopicCounts) VALUE (#{Title},#{num})")
    void insertCategorywithnum(@Param("Title") String Title, @Param("num") long num);

    @Delete("DELETE FROM Category WHERE Id = #{Id}")
    void deleteCategoryById(@Param("Id") String Id);

    @Update("UPDATE Category SET TopicCounts = #{num} WHERE Id = #{Id}")
    void modifyCategoryCounts(@Param("Id") String Id, @Param("num") long num);

    @Select("SELECT * FROM Category WHERE Category = #{cate}")
    Category findCategoryByName(@Param("cate") String cate);


}
