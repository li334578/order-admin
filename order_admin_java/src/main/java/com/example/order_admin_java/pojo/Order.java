package com.example.order_admin_java.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.order_admin_java.dto.PageBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 3:03
 * @Description 订单实体
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_order")
public class Order extends PageBean<Order> {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户联系电话
     */
    private String customerPhone;
    /**
     * 客户地址
     */
    private String customerAddress;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 制单人
     */
    private String createUserName;
    /**
     * 订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 订单日期筛选
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private Date startCreateTime;
    /**
     * 订单日期筛选
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private Date endCreateTime;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 已收款
     */
    private BigDecimal receiveMoney;
    /**
     * 总面积
     */
    private BigDecimal totalArea;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否付款
     */
    private Boolean payStatus;
    /**
     * 是否发货
     */
    private Boolean sendStatus;

    /**
     * 货物清单
     */
    @TableField(exist = false)
    private List<Goods> goodsList;

    /**
     * 删除的货物id
     */
    @TableField(exist = false)
    private List<Long> delGoodsIdList;
}
