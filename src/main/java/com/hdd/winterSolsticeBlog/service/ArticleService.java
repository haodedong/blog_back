package com.hdd.winterSolsticeBlog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdd.winterSolsticeBlog.common.vo.ResponsePage;
import com.hdd.winterSolsticeBlog.dto.ArticleDTO;
import com.hdd.winterSolsticeBlog.dto.request.GetArticlePageListRequest;
import com.hdd.winterSolsticeBlog.entity.Article;
import com.hdd.winterSolsticeBlog.repository.ArticleRepository;
import com.hdd.winterSolsticeBlog.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ResponsePage<ArticleVO> getArticlePageList(GetArticlePageListRequest request) {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<Article> articles = articleRepository.findAllOrderByCreateTime();
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
        List<Article> articles = articleRepository.findByCategoryOrderByCreateTime(category);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new ResponsePage<>(articleVOs, pageInfo.getTotal());
    }

    public ResponsePage<ArticleVO> getArticlesByTag(String tag, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Article> articles = articleRepository.findByTagOrderByCreateTime(tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        List<ArticleVO> articleVOs = articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new ResponsePage<>(articleVOs, pageInfo.getTotal());
    }

    public ResponsePage<ArticleVO> searchArticles(String keyword, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Article> articles = articleRepository.searchByKeywordOrderByCreateTime(keyword);
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
        vo.setTags(article.getTags());
        vo.setAuthor(article.getAuthor());
        vo.setCreateTime(article.getCreateTime());
        return vo;
    }

    private Article convertToEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        // ArticleDTO 中是 categoryId，需要根据 categoryId 获取分类名称
        // 这里暂时设置为 null，您可能需要通过 categoryId 查询分类名称
        article.setCategory(null);
        // ArticleDTO 中是 tagIds，需要根据 tagIds 获取标签名称列表
        // 这里暂时设置为 null，您可能需要通过 tagIds 查询标签名称
        article.setTags(null);
        // ArticleDTO 中是 authorId，需要根据 authorId 获取作者名称
        // 这里暂时设置为 null，您可能需要通过 authorId 查询作者名称
        article.setAuthor(null);
        article.setCreateTime(dto.getCreatedAt()); // ArticleDTO 中是 createdAt
        return article;
    }
}