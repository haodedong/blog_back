package com.hdd.winterSolsticeBlog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdd.winterSolsticeBlog.common.vo.ResponsePage;
import com.hdd.winterSolsticeBlog.dto.ArticleDTO;
import com.hdd.winterSolsticeBlog.dto.request.GetArticlePageListRequest;
import com.hdd.winterSolsticeBlog.entity.Article;
import com.hdd.winterSolsticeBlog.entity.Category;
import com.hdd.winterSolsticeBlog.entity.Tag;
import com.hdd.winterSolsticeBlog.repository.ArticleRepository;
import com.hdd.winterSolsticeBlog.repository.CategoryRepository;
import com.hdd.winterSolsticeBlog.repository.TagRepository;
import com.hdd.winterSolsticeBlog.repository.AuthorRepository;
import com.hdd.winterSolsticeBlog.vo.ArticleVO;
import com.hdd.winterSolsticeBlog.vo.CategoryVO;
import com.hdd.winterSolsticeBlog.vo.TagVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * 获取文章列表（Controller需要的方法）
     */
    public ResponsePage<ArticleVO> getArticles(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Article::getTitle, keyword)
                       .or()
                       .like(Article::getContent, keyword);
        }

        queryWrapper.orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return ResponsePage.of(articleVOs, pageInfo);
    }

    /**
     * 获取文章详情（Controller需要的方法）
     */
    public ArticleVO getArticleDetail(Integer id) {
        Article article = articleRepository.selectById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }

        // 增加阅读次数 - 使用原生SQL
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, id)
                    .setSql("read_count = read_count + 1");
        articleRepository.update(null, updateWrapper);

        return convertToVO(article);
    }

    /**
     * 获取相关文章
     */
    public List<ArticleVO> getRelatedArticles(Integer id) {
        Article currentArticle = articleRepository.selectById(id);
        if (currentArticle == null) {
            throw new RuntimeException("文章不存在");
        }

        // 根据相同分类获取相关文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategory, currentArticle.getCategory())
                   .ne(Article::getId, id)
                   .orderByDesc(Article::getCreateTime)
                   .last("LIMIT 5");

        List<Article> relatedArticles = articleRepository.selectList(queryWrapper);

        return relatedArticles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 切换文章点赞状态
     */
    public Map<String, Object> toggleArticleLike(Integer id) {
        Article article = articleRepository.selectById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }

        // 增加点赞数 - 使用原生SQL
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, id)
                    .setSql("like_count = like_count + 1");
        articleRepository.update(null, updateWrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("isLiked", true);
        result.put("likeCount", 1); // 简化处理
        return result;
    }

    /**
     * 获取分类列表
     */
    public List<CategoryVO> getCategories() {
        List<Category> categories = categoryRepository.selectList(null);
        return categories.stream()
                .map(this::convertToCategoryVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取标签列表
     */
    public List<TagVO> getTags() {
        List<Tag> tags = tagRepository.selectList(null);
        return tags.stream()
                .map(this::convertToTagVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据分类获取文章（Controller需要的方法）
     */
    public ResponsePage<ArticleVO> getArticlesByCategory(Integer categoryId, Integer page, Integer size) {
        // 先根据分类ID获取分类名称
        Category category = categoryRepository.selectById(categoryId);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }

        PageHelper.startPage(page, size);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategory, category.getName())
                   .orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return ResponsePage.of(articleVOs, pageInfo);
    }

    /**
     * 根据标签获取文章（Controller需要的方法）
     */
    public ResponsePage<ArticleVO> getArticlesByTag(Integer tagId, Integer page, Integer size) {
        // 先根据标签ID获取标签名称
        Tag tag = tagRepository.selectById(tagId);
        if (tag == null) {
            throw new RuntimeException("标签不存在");
        }

        PageHelper.startPage(page, size);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("JSON_CONTAINS(tags, JSON_QUOTE({0}))", tag.getName())
                   .orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return ResponsePage.of(articleVOs, pageInfo);
    }

    /**
     * 搜索文章（Controller需要的方法）
     */
    public ResponsePage<ArticleVO> searchArticles(String keyword, Integer page, Integer size) {
        return getArticles(page, size, keyword);
    }

    // 原有方法保持不变
    public ResponsePage<ArticleVO> getArticlePageList(GetArticlePageListRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return ResponsePage.of(articleVOs, pageInfo);
    }

    public ArticleVO getArticleById(Integer id) {
        Article article = articleRepository.selectById(id);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        return convertToVO(article);
    }

    public ResponsePage<ArticleVO> getArticlesByCategory(String category, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategory, category)
                   .orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new ResponsePage<>(articleVOs, pageInfo.getTotal());
    }

    public ResponsePage<ArticleVO> getArticlesByTag(String tag, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("JSON_CONTAINS(tags, JSON_QUOTE({0}))", tag)
                   .orderByDesc(Article::getCreateTime);

        List<Article> articles = articleRepository.selectList(queryWrapper);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new ResponsePage<>(articleVOs, pageInfo.getTotal());
    }

    public void checkArticleInfo(ArticleDTO request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new RuntimeException("文章标题不能为空");
        }
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new RuntimeException("文章内容不能为空");
        }
    }

    public void saveOrUpdateArticle(ArticleDTO request) {
        Article article = convertToEntity(request);
        if (article.getId() == null) {
            articleRepository.insert(article);
        } else {
            articleRepository.updateById(article);
        }
    }

    private ArticleVO convertToVO(Article article) {
        ArticleVO vo = new ArticleVO();
        vo.setId(article.getId());
        vo.setTitle(article.getTitle());
        vo.setContent(article.getContent());
        vo.setCategory(article.getCategory());
        vo.setAuthor(article.getAuthor());
        vo.setCreateTime(article.getCreateTime());

        // 设置默认值
        vo.setSummary(""); // 如果需要摘要，可以从content中截取
        vo.setCover(""); // 默认封面
        vo.setReadCount(0); // 默认阅读次数
        vo.setLikeCount(0); // 默认点赞次数
        vo.setUpdateTime(article.getCreateTime()); // 使用创建时间

        // 转换标签
        if (article.getTags() != null) {
            List<TagVO> tagVOs = article.getTags().stream()
                    .map(tagName -> {
                        TagVO tagVO = new TagVO();
                        tagVO.setName(tagName);
                        return tagVO;
                    })
                    .collect(Collectors.toList());
            vo.setTags(tagVOs);
        }

        return vo;
    }

    private CategoryVO convertToCategoryVO(Category category) {
        CategoryVO vo = new CategoryVO();
        vo.setId(category.getId());
        vo.setName(category.getName());
        vo.setDescription(category.getDescription() != null ? category.getDescription() : "");

        // 统计该分类下的文章数量
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategory, category.getName());
        Long count = articleRepository.selectCount(queryWrapper);
        vo.setArticleCount(count.intValue());

        return vo;
    }

    private TagVO convertToTagVO(Tag tag) {
        TagVO vo = new TagVO();
        vo.setId(tag.getId());
        vo.setName(tag.getName());
        vo.setColor(tag.getColor() != null ? tag.getColor() : "#007bff");

        // 统计该标签下的文章数量
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("JSON_CONTAINS(tags, JSON_QUOTE({0}))", tag.getName());
        Long count = articleRepository.selectCount(queryWrapper);
        vo.setArticleCount(count.intValue());

        return vo;
    }

    private Article convertToEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());

        // 根据实际的Article实体类字段来设置
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.selectById(dto.getCategoryId());
            if (category != null) {
                article.setCategory(category.getName());
            }
        }

        if (dto.getAuthorId() != null) {
            article.setAuthor(authorRepository.selectById(dto.getAuthorId()).getName());
        }

        article.setCreateTime(dto.getCreatedAt());
        return article;
    }
}