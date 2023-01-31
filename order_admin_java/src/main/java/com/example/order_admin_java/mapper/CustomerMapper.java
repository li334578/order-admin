package com.example.order_admin_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order_admin_java.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date 31/1/2023 0031 下午 4:46
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
