package com.hdd.winterSolsticeBlog.service;

import com.hdd.winterSolsticeBlog.entity.Category;
import com.hdd.winterSolsticeBlog.repository.CategoryRepository;
import com.hdd.winterSolsticeBlog.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryVO> getAllCategories() {
        List<Category> categories = categoryRepository.selectList(null);
        return categories.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private CategoryVO convertToVO(Category category) {
        CategoryVO vo = new CategoryVO();
        vo.setId(category.getId());
        vo.setName(category.getName());
        return vo;
    }
}