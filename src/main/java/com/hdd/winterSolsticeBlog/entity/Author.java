package com.hdd.winterSolsticeBlog.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("authors")
@ApiModel(value = "作者实体类")
public class Author {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "作者ID")
    private Integer id;

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "作者简介")
    private String bio;

    @ApiModelProperty(value = "头像图片(base64编码)")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}