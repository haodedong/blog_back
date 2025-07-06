package com.hdd.winterSolsticeBlog.controller;

import com.hdd.winterSolsticeBlog.common.vo.JsonResult;
import com.hdd.winterSolsticeBlog.common.vo.ResponsePage;
import com.hdd.winterSolsticeBlog.dto.ArticleDTO;
import com.hdd.winterSolsticeBlog.dto.request.GetArticlePageListRequest;
import com.hdd.winterSolsticeBlog.service.ArticleService;
import com.hdd.winterSolsticeBlog.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "博客文章模块")
@RestController
@RequestMapping("/api/blog/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取文章列表")
    @GetMapping
    public JsonResult<ResponsePage<ArticleVO>> getArticles(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        GetArticlePageListRequest request = new GetArticlePageListRequest();
        request.setPageNo(page);
        request.setPageSize(size);
        return JsonResult.success(articleService.getArticlePageList(request));
    }

    @ApiOperation(value = "根据ID查询博客文章详情")
    @GetMapping("/{id}")
    public JsonResult<ArticleVO> getArticleDetail(@PathVariable(name = "id") Integer id) {
        return JsonResult.success(articleService.getArticleById(id));
    }

    @ApiOperation(value = "根据分类获取文章")
    @GetMapping("/category/{category}")
    public JsonResult<ResponsePage<ArticleVO>> getArticlesByCategory(@PathVariable String category,
                                                                     @RequestParam(defaultValue = "1") Integer page,
                                                                     @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.getArticlesByCategory(category, page, size));
    }

    @ApiOperation(value = "根据标签获取文章")
    @GetMapping("/tag/{tag}")
    public JsonResult<ResponsePage<ArticleVO>> getArticlesByTag(@PathVariable String tag,
                                                                @RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.getArticlesByTag(tag, page, size));
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/search")
    public JsonResult<ResponsePage<ArticleVO>> searchArticles(@RequestParam String keyword,
                                                              @RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer size) {
        return JsonResult.success(articleService.searchArticles(keyword, page, size));
    }

    @ApiOperation(value = "保存博客文章")
    @PostMapping("/")
    public JsonResult<Void> saveArticle(@Validated @RequestBody ArticleDTO request) {
        articleService.checkArticleInfo(request);
        articleService.saveOrUpdateArticle(request);
        return JsonResult.success(null);
    }
}