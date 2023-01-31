package com.example.order_admin_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_admin_java.mapper.GoodsMapper;
import com.example.order_admin_java.pojo.Goods;
import com.example.order_admin_java.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @Date 31/1/2023 0031 下午 4:42
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
