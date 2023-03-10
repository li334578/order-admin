package com.example.order_admin_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order_admin_java.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date 31/1/2023 0031 下午 4:45
 * @Description 订单Mapper
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from tb_order where order_number like #{orderNumber}")
    List<Order> getListByOrderNumberLeftMatching(@Param("orderNumber") String orderNumber);
}
