package com.hdd.winterSolsticeBlog.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * 分页查询结果封装类
 *
 * @param <T> 查询结果的数据类型
 * @author haodedong
 */
@ApiModel(value = "分页查询结果")
@Data
public class ResponsePage<T> {
    @ApiModelProperty(value = "当前页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页查询数量")
    private Integer pageSize;

    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Integer totalPages;

    @ApiModelProperty(value = "查询结果数据")
    private List<T> list;

    public ResponsePage() {}

    public ResponsePage(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public ResponsePage(List<T> list, Long total, Integer pageNo, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = calculateTotalPages(total, pageSize);
    }

    // 在 ResponsePage 类中添加这个静态方法
    public static <T> ResponsePage<T> of(List<T> list, PageInfo<?> pageInfo) {
        return new ResponsePage<>(
                list,
                pageInfo.getTotal(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize()
        );
    }

    // 计算总页数的辅助方法
    private Integer calculateTotalPages(Long total, Integer pageSize) {
        if (total == null || pageSize == null || pageSize == 0) {
            return 0;
        }
        return (int) Math.ceil((double) total / pageSize);
    }

    // 设置总记录数时自动计算总页数
    public void setTotal(Long total) {
        this.total = total;
        this.totalPages = calculateTotalPages(total, this.pageSize);
    }

    // 设置每页数量时自动计算总页数
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.totalPages = calculateTotalPages(this.total, pageSize);
    }
}