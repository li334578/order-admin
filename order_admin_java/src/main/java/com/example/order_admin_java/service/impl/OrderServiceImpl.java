package com.example.order_admin_java.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_admin_java.mapper.OrderMapper;
import com.example.order_admin_java.pojo.Order;
import com.example.order_admin_java.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Date 31/1/2023 0031 下午 4:42
 * @Description 订单ServiceImpl
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 根据条件查询订单信息
     */
    @Override
    public Collection<Order> listByCondition(Order order) {
        QueryWrapper<Order> queryWrapper = buildOrderQueryWrapper(order);
        if (Objects.nonNull(order.getStart()) && Objects.nonNull(order.getSize())) {
            queryWrapper.last(StrUtil.format(" limit {},{}", order.getStart(), order.getSize()));
        }
        return this.list(queryWrapper);
    }

    private QueryWrapper<Order> buildOrderQueryWrapper(Order order) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(order.getCustomerId())) {
            queryWrapper.eq("customer_id", order.getCustomerId());
        }
        if (Objects.nonNull(order.getStartCreateTime()) && Objects.nonNull(order.getEndCreateTime())) {
            queryWrapper.between("create_time", order.getStartCreateTime(), order.getEndCreateTime());
        }
        if (Objects.nonNull(order.getPayStatus())) {
            queryWrapper.eq("pay_status", order.getPayStatus());
        }
        if (Objects.nonNull(order.getSendStatus())) {
            queryWrapper.eq("send_status", order.getSendStatus());
        }
        return queryWrapper;
    }

    /**
     * 根据条件查询订单数量
     */
    @Override
    public Integer countByCondition(Order order) {
        QueryWrapper<Order> queryWrapper = buildOrderQueryWrapper(order);
        return this.count(queryWrapper);
    }

    /**
     * 根据订单号左匹配
     */
    @Override
    public List<Order> getListByOrderNumberLeftMatching(String orderNumber) {
        orderNumber = orderNumber + "%";
        return orderMapper.getListByOrderNumberLeftMatching(orderNumber);
    }
}
