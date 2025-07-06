package com.hdd.winterSolsticeBlog.controller;

import com.hdd.winterSolsticeBlog.common.vo.JsonResult;
import com.hdd.winterSolsticeBlog.service.CategoryService;
import com.hdd.winterSolsticeBlog.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "博客分类模块")
@RestController
@RequestMapping("/api/blog/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分类列表")
    @GetMapping
    public JsonResult<List<CategoryVO>> getCategories() {
        return JsonResult.success(categoryService.getAllCategories());
    }
}