package com.hdd.winterSolsticeBlog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdd.winterSolsticeBlog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagRepository extends BaseMapper<Tag> {
}