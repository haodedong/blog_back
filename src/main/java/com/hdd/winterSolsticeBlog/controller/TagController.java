package com.hdd.winterSolsticeBlog.controller;

import com.hdd.winterSolsticeBlog.common.vo.JsonResult;
import com.hdd.winterSolsticeBlog.service.TagService;
import com.hdd.winterSolsticeBlog.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "博客标签模块")
@RestController
@RequestMapping("/api/blog/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "获取标签列表")
    @GetMapping
    public JsonResult<List<TagVO>> getTags() {
        return JsonResult.success(tagService.getAllTags());
    }
}