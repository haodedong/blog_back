package com.hdd.winterSolsticeBlog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdd.winterSolsticeBlog.entity.Author;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorRepository extends BaseMapper<Author> {
}