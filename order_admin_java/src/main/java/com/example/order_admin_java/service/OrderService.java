package com.example.order_admin_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order_admin_java.pojo.Order;

import java.util.Collection;
import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 4:41
 * @Description 订单Service
 * @Version 1.0.0
 * @Author liwenbo
 */
public interface OrderService extends IService<Order> {
    /**
     * 根据条件查询订单信息
     */
    Collection<Order> listByCondition(Order order);

    /**
     * 根据条件查询订单数量
     */
    Integer countByCondition(Order order);

    /**
     * 根据订单号左匹配
     */
    List<Order> getListByOrderNumberLeftMatching(String orderNumber);
}
