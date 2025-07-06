package com.hdd.winterSolsticeBlog.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleVO {
    private Integer id;
    private String title;
    private String content;
    private String category;
    private List<String> tags;
    private String author;
    private LocalDateTime createTime;
}