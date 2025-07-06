package com.hdd.winterSolsticeBlog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "作者视图对象")
public class AuthorVO {

    @ApiModelProperty(value = "作者ID")
    private Integer id;

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "作者简介")
    private String bio;

    @ApiModelProperty(value = "头像图片(base64编码)")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}