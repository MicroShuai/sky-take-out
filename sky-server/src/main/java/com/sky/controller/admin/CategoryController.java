package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageBean;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {

    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //修改分类
    @PutMapping
    public Result<Object> updateCategory(@RequestBody Category category) {
        log.info("修改分类:{}", category);
        categoryService.updateCategory(category);
        return Result.success();
    }

    //分类分页查询
    @GetMapping("/page")
    public Result<PageBean> queryPageCategory(@ModelAttribute CategoryPageQueryDTO categoryPageQueryDTO) {
        System.out.println(categoryPageQueryDTO);
        return Result.success(categoryService.queryPageCategory(categoryPageQueryDTO));
    }


    //启用、禁用分类
    @PostMapping("/status/{status}")
    public Result<Object> startOrStop(@PathVariable String status, Long id) {
        log.info("分类状态:{}{}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    //新增分类
    @PostMapping
    public Result<Object> addCategory(@RequestBody Category category) {
        log.info("新增分类:{}", category);
        categoryService.addCategory(category);
        return Result.success();
    }

    //根据id删除分类
    @DeleteMapping
    public Result<Object> deleteCategoryById(Long id) {
        log.info("根据id删除分类:{}", id);
        categoryService.deleteCategoryById(id);
        return Result.success();
    }

    //根据类型查询分类
    @GetMapping
    public Result<Object> list(Integer type) {
        return Result.success(categoryService.list(type));
    }

}
