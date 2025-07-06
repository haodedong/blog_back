package com.hdd.winterSolsticeBlog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdd.winterSolsticeBlog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleRepository extends BaseMapper<Article> {

    @Select("SELECT * FROM articles ORDER BY create_time DESC")
    List<Article> findAllOrderByCreateTime();

    @Select("SELECT * FROM articles WHERE category = #{category} ORDER BY create_time DESC")
    List<Article> findByCategoryOrderByCreateTime(@Param("category") String category);

    @Select("SELECT a.* FROM articles a JOIN article_tags at ON a.id = at.article_id WHERE at.tag = #{tag} ORDER BY a.create_time DESC")
    List<Article> findByTagOrderByCreateTime(@Param("tag") String tag);

    @Select("SELECT * FROM articles WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') ORDER BY create_time DESC")
    List<Article> searchByKeywordOrderByCreateTime(@Param("keyword") String keyword);
}