package com.hdd.winterSolsticeBlog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "标签视图对象")
public class TagVO {

    @ApiModelProperty(value = "标签ID")
    private Integer id;

    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "标签颜色")
    private String color;

    @ApiModelProperty(value = "文章数量")
    private Integer articleCount;
}