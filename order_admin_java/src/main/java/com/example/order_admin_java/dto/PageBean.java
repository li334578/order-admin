package com.example.order_admin_java.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Date 31/1/2023 0031 下午 5:00
 * @Description 分页工具父类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class PageBean<T> implements Serializable {
    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer current;
    /**
     * 起始位置
     */
    @TableField(exist = false)
    private Integer start;
    /**
     * 查询数据条数
     */
    @TableField(exist = false)
    private Integer size;
    /**
     * 数据
     */
    @TableField(exist = false)
    private Collection<T> data;
    /**
     * 总数据条数
     */
    @TableField(exist = false)
    private Integer total;
}
