package com.example.order_admin_java.controller;

import com.example.order_admin_java.dto.RespBean;
import com.example.order_admin_java.pojo.Customer;
import com.example.order_admin_java.pojo.Order;
import com.example.order_admin_java.service.CustomerService;
import com.example.order_admin_java.service.OrderService;
import com.example.order_admin_java.utils.PageUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

/**
 * @Date 31/1/2023 0031 下午 3:03
 * @Description 订单控制器
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private CustomerService customerService;

    @PostMapping("/add")
    public RespBean<Void> addOder(@RequestBody Order order) {
        if (Objects.isNull(order.getCustomerId())) {
            // 保存下客户
            Customer customer = new Customer();
            customer.setCustomerName(order.getCustomerName());
            customer.setCustomerPhone(order.getCustomerPhone());
            customer.setCustomerAddress(order.getCustomerAddress());
            customerService.save(customer);
            order.setCustomerId(customer.getId());
        }
        // 生成订单号 dd20230131001

        orderService.save(order);
        return RespBean.success();
    }

    @PostMapping("/list")
    public RespBean<Order> listOrder(@RequestBody Order order) {
        PageUtils.handlerPageStart(order);
        Collection<Order> orders = orderService.listByCondition(order);
        Integer count = orderService.countByCondition(order);
        order.setTotal(count);
        order.setData(orders);
        return RespBean.success(order);
    }

    @DeleteMapping("/del/{orderId}")
    public RespBean<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.removeById(orderId);
        return RespBean.success();
    }
}
