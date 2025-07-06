package com.hdd.winterSolsticeBlog.controller;

import com.hdd.winterSolsticeBlog.common.vo.JsonResult;
import com.hdd.winterSolsticeBlog.dto.AuthorDTO;
import com.hdd.winterSolsticeBlog.service.AuthorService;
import com.hdd.winterSolsticeBlog.vo.AuthorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = "博客作者模块")
@RestController
@RequestMapping("/api/blog/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "获取作者信息")
    @GetMapping("")
    public JsonResult<AuthorVO> getAuthorInfo() {
        return JsonResult.success(authorService.getAuthorInfo());
    }

    @ApiOperation(value = "获取作者信息")
    @GetMapping("/{id}")
    public JsonResult<AuthorVO> getAuthorInfo(@ApiParam(value = "作者ID") @PathVariable Integer id) {
        return JsonResult.success(authorService.getAuthorInfo(id));
    }

    @ApiOperation(value = "新增作者")
    @PostMapping
    public JsonResult<AuthorVO> createAuthor(
            @ApiParam(value = "作者姓名") @RequestParam("name") String name,
            @ApiParam(value = "作者简介") @RequestParam(value = "bio", required = false) String bio,
            @ApiParam(value = "头像文件") @RequestParam(value = "avatar", required = false) MultipartFile avatar) {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(name);
        authorDTO.setBio(bio);

        return JsonResult.success(authorService.createAuthor(authorDTO, avatar));
    }
    @ApiOperation(value = "更新作者信息")
    @PutMapping("/{id}")
    public JsonResult<AuthorVO> updateAuthor(
            @ApiParam(value = "作者ID") @PathVariable Integer id,
            @Valid @RequestBody AuthorDTO authorDTO) {
        return JsonResult.success(authorService.updateAuthor(id, authorDTO));
    }

    @ApiOperation(value = "上传作者头像")
    @PostMapping("/{id}/avatar")
    public JsonResult<String> uploadAvatar(
            @ApiParam(value = "作者ID") @PathVariable Integer id,
            @ApiParam(value = "头像文件") @RequestParam("file") MultipartFile file) {
        return JsonResult.success(authorService.uploadAvatar(id, file));
    }
}