package com.example.order_admin_java.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Date 31/1/2023 0031 下午 3:11
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class Customer {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户手机号
     */
    private String customerPhone;
    /**
     * 客户地址
     */
    private String customerAddress;
}
