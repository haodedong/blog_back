package com.hdd.winterSolsticeBlog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author hdd
 * @Date 2023-11-11
 **/
@ApiModel(description = "文章DTO")
@Data
public class ArticleDTO {
    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "标签ID列表")
    private List<Integer> tagIds;

    @ApiModelProperty(value = "作者ID")
    private Integer authorId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;
}