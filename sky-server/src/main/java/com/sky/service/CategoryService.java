package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageBean;
import org.apache.poi.ss.formula.functions.T;

public interface CategoryService {

    void updateCategory(Category category);

    void startOrStop(String status, Long id);

    void addCategory(Category category);

    void deleteCategoryById(Long id);

    PageBean queryPageCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    Object list(Integer type);
}
