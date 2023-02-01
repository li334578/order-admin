package com.example.order_admin_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order_admin_java.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 4:45
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from order where order_number like concat('',#{orderNumber},'%')")
    List<Order> getListByOrderNumberLeftMatching(String orderNumber);
}
