package com.example.order_admin_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_admin_java.mapper.CustomerMapper;
import com.example.order_admin_java.pojo.Customer;
import com.example.order_admin_java.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * @Date 31/1/2023 0031 下午 4:47
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
}
