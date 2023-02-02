package com.example.order_admin_java.controller;

import com.example.order_admin_java.dto.RespBean;
import com.example.order_admin_java.pojo.Customer;
import com.example.order_admin_java.service.CustomerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 1/2/2023 0001 下午 1:19
 * @Description 客户controller
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/customer/")
@CrossOrigin
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/getCustomerList")
    public RespBean<List<Customer>> getCustomerList() {
        return RespBean.success(customerService.list());
    }
}
