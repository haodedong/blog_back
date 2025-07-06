package com.hdd.winterSolsticeBlog.service;

import com.hdd.winterSolsticeBlog.dto.AuthorDTO;
import com.hdd.winterSolsticeBlog.entity.Author;
import com.hdd.winterSolsticeBlog.repository.AuthorRepository;
import com.hdd.winterSolsticeBlog.vo.AuthorVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorVO getAuthorInfo() {
        // 假设获取第一个作者信息，您可以根据需要调整逻辑
        Author author = authorRepository.selectList(null).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到作者信息"));
        return convertToVO(author);
    }

    public List<AuthorVO> getAllAuthors() {
        List<Author> authors = authorRepository.selectList(null);
        return authors.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private AuthorVO convertToVO(Author author) {
        AuthorVO vo = new AuthorVO();
        vo.setId(author.getId());
        vo.setName(author.getName());
        vo.setBio(author.getBio());
        vo.setAvatar(author.getAvatar());
        return vo;
    }

   

   
    public AuthorVO getAuthorInfo(Integer id) {
        Author author = authorRepository.selectById(id);
        if (author == null) {
            throw new RuntimeException("作者不存在");
        }

        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(author, authorVO);
        return authorVO;
    }


    public AuthorVO createAuthor(AuthorDTO authorDTO, MultipartFile avatar) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDTO, author);

        // 处理头像文件
        if (avatar != null && !avatar.isEmpty()) {
            String base64Avatar = convertToBase64(avatar);
            author.setAvatar(base64Avatar);
        }

        authorRepository.insert(author);

        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(author, authorVO);
        return authorVO;
    }

    private String convertToBase64(MultipartFile file) {
        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("只能上传图片文件");
        }

        // 检查文件大小（限制为2MB）
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new RuntimeException("图片文件大小不能超过2MB");
        }

        try {
            byte[] bytes = file.getBytes();
            return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("文件处理失败: " + e.getMessage());
        }
    }

   
    public AuthorVO updateAuthor(Integer id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.selectById(id);
        if (existingAuthor == null) {
            throw new RuntimeException("作者不存在");
        }

        BeanUtils.copyProperties(authorDTO, existingAuthor);
        existingAuthor.setId(id);

        authorRepository.updateById(existingAuthor);

        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(existingAuthor, authorVO);
        return authorVO;
    }

   
    public String uploadAvatar(Integer id, MultipartFile file) {
        Author author = authorRepository.selectById(id);
        if (author == null) {
            throw new RuntimeException("作者不存在");
        }

        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("只能上传图片文件");
        }

        // 检查文件大小（限制为2MB）
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new RuntimeException("图片文件大小不能超过2MB");
        }

        try {
            // 将文件转换为Base64字符串
            byte[] bytes = file.getBytes();
            String base64Avatar = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);

            // 更新作者头像
            author.setAvatar(base64Avatar);
            authorRepository.updateById(author);

            return base64Avatar;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
}