package com.hdd.winterSolsticeBlog.controller;

import com.hdd.winterSolsticeBlog.common.vo.JsonResult;
import com.hdd.winterSolsticeBlog.common.vo.ResponsePage;
import com.hdd.winterSolsticeBlog.service.ArticleService;
import com.hdd.winterSolsticeBlog.vo.ArticleVO;
import com.hdd.winterSolsticeBlog.vo.CategoryVO;
import com.hdd.winterSolsticeBlog.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "博客文章模块")
@RestController
@RequestMapping("/api/blog")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取文章列表")
    @GetMapping("/articles")
    public JsonResult<ResponsePage<ArticleVO>> getArticles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return JsonResult.success(articleService.getArticles(page, size, keyword));
    }

    @ApiOperation(value = "获取文章详情")
    @GetMapping("/article/{id}")
    public JsonResult<ArticleVO> getArticleDetail(@ApiParam(value = "文章ID") @PathVariable Integer id) {
        return JsonResult.success(articleService.getArticleDetail(id));
    }

    @ApiOperation(value = "获取相关文章")
    @GetMapping("/article/{id}/related")
    public JsonResult<List<ArticleVO>> getRelatedArticles(@ApiParam(value = "文章ID") @PathVariable Integer id) {
        return JsonResult.success(articleService.getRelatedArticles(id));
    }

    @ApiOperation(value = "切换文章点赞状态")
    @PostMapping("/article/{id}/like")
    public JsonResult<Map<String, Object>> toggleArticleLike(@ApiParam(value = "文章ID") @PathVariable Integer id) {
        return JsonResult.success(articleService.toggleArticleLike(id));
    }

//    @ApiOperation(value = "获取分类列表")
//    @GetMapping("/categories")
//    public JsonResult<List<CategoryVO>> getCategories() {
//        return JsonResult.success(articleService.getCategories());
//    }

//    @ApiOperation(value = "获取标签列表")
//    @GetMapping("/tags")
//    public JsonResult<List<TagVO>> getTags() {
//        return JsonResult.success(articleService.getTags());
//    }

    @ApiOperation(value = "根据分类获取文章")
    @GetMapping("/articles/category/{category}")
    public JsonResult<ResponsePage<ArticleVO>> getArticlesByCategory(
            @ApiParam(value = "分类ID") @PathVariable Integer category,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.getArticlesByCategory(category, page, size));
    }

    @ApiOperation(value = "根据标签获取文章")
    @GetMapping("/articles/tag/{tag}")
    public JsonResult<ResponsePage<ArticleVO>> getArticlesByTag(
            @ApiParam(value = "标签ID") @PathVariable Integer tag,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.getArticlesByTag(tag, page, size));
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public JsonResult<ResponsePage<ArticleVO>> searchArticles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.searchArticles(keyword, page, size));
    }
}