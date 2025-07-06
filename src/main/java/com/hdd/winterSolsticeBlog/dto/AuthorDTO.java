package com.hdd.winterSolsticeBlog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "作者数据传输对象")
public class AuthorDTO {

    @ApiModelProperty(value = "作者姓名")
    @NotBlank(message = "作者姓名不能为空")
    @Size(max = 50, message = "作者姓名长度不能超过50个字符")
    private String name;

    @ApiModelProperty(value = "作者简介")
    @Size(max = 500, message = "作者简介长度不能超过500个字符")
    private String bio;

    @ApiModelProperty(value = "头像图片(base64编码)")
    private String avatar;
}