package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper {

    void updateCategory(Category category);

    @Update("update category set status = #{status} where id = #{id} ")
    void startOrStop(String status, Long id);

    @Insert("INSERT INTO category values (null,#{type},#{name},#{sort},0,#{createTime},#{updateTime},null,null  )")
    void addCategory(Category category);

    @Delete("DELETE FROM category where id =#{id}")
    void deleteCategoryById(Long id);

    Page<Category> queryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    Object list(Integer type);
}
