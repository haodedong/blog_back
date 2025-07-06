package com.hdd.winterSolsticeBlog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdd.winterSolsticeBlog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository extends BaseMapper<Category> {
}