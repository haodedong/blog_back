package com.hdd.winterSolsticeBlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("articles")
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String category;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    private String author;

    @TableField("create_time")
    private LocalDateTime createTime;
}