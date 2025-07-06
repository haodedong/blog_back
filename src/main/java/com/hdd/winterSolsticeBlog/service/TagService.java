package com.hdd.winterSolsticeBlog.service;

import com.hdd.winterSolsticeBlog.entity.Tag;
import com.hdd.winterSolsticeBlog.repository.TagRepository;
import com.hdd.winterSolsticeBlog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<TagVO> getAllTags() {
        List<Tag> tags = tagRepository.selectList(null);
        return tags.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private TagVO convertToVO(Tag tag) {
        TagVO vo = new TagVO();
        vo.setId(tag.getId());
        vo.setName(tag.getName());
        return vo;
    }
}