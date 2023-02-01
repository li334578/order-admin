package com.example.order_admin_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order_admin_java.pojo.Goods;

import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 4:42
 * @Description 货物service
 * @Version 1.0.0
 * @Author liwenbo
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 根据订单号删除货物信息
     */
    void removeByOrderId(Long orderId);

    /**
     * 根据订单id获取货物信息
     */
    List<Goods> getByOrderId(Long orderId);
}
