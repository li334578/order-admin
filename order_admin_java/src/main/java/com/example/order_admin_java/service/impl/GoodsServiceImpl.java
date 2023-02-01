package com.example.order_admin_java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_admin_java.mapper.GoodsMapper;
import com.example.order_admin_java.pojo.Goods;
import com.example.order_admin_java.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 4:42
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    /**
     * 根据订单号删除货物信息
     */
    @Override
    public void removeByOrderId(Long orderId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        this.remove(queryWrapper);
    }

    /**
     * 根据订单id获取货物信息
     */
    @Override
    public List<Goods> getByOrderId(Long orderId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return this.list(queryWrapper);
    }
}
