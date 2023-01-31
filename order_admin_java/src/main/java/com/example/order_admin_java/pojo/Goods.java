package com.example.order_admin_java.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date 31/1/2023 0031 下午 4:32
 * @Description 商品
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class Goods {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 产品名称
     */
    private String goodsName;
    /**
     * 宽度 mm
     */
    private BigDecimal goodsWidth;
    /**
     * 长度 mm
     */
    private BigDecimal goodsLength;
    /**
     * 总数
     */
    private BigDecimal total;
    /**
     * 面积 平方米
     */
    private BigDecimal area;
    /**
     * 单价
     */
    private BigDecimal goodsPrice;
    /**
     * 加工需求
     */
    private String processingRequirements;
    /**
     * 加工费用
     */
    private BigDecimal processingExpenses;
    /**
     * 总金额
     */
    private BigDecimal totalMoney;
    /**
     * 备注
     */
    private String remark;
    /**
     * 生产流程
     */
    private String productionProcess;
    /**
     * 关联订单id
     */
    private Long orderId;
}
