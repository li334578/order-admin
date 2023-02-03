package com.example.order_admin_java.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.order_admin_java.dto.RespBean;
import com.example.order_admin_java.pojo.Customer;
import com.example.order_admin_java.pojo.Goods;
import com.example.order_admin_java.pojo.Order;
import com.example.order_admin_java.service.CustomerService;
import com.example.order_admin_java.service.GoodsService;
import com.example.order_admin_java.service.OrderService;
import com.example.order_admin_java.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date 31/1/2023 0031 下午 3:03
 * @Description 订单控制器
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/order/")
@Slf4j
@CrossOrigin
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private CustomerService customerService;
    @Resource
    private GoodsService goodsService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Pattern pattern = Pattern.compile("dd\\d{8}(\\d{3,})");

    @PostMapping("/add")
    @Transactional
    public RespBean<Void> addOder(@RequestBody Order order) {
        if (Objects.isNull(order.getCustomerId())) {
            // 根据客户姓名和手机号查询客户是否存在
            QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_name", order.getCustomerName());
            queryWrapper.eq("customer_phone", order.getCustomerPhone());
            Customer customer = customerService.getOne(queryWrapper);
            if (Objects.isNull(customer)) {
                // 保存下客户
                customer = new Customer();
                customer.setCustomerName(order.getCustomerName());
                customer.setCustomerPhone(order.getCustomerPhone());
                customer.setCustomerAddress(order.getCustomerAddress());
                customerService.save(customer);
            }
            order.setCustomerId(customer.getId());
        }
        // 生成订单号 dd20230131001
        String orderNumber = getOrderNumber();
        order.setOrderNumber(orderNumber);
        orderService.save(order);
        // 保存订单下的货物信息
        order.getGoodsList().forEach(item -> item.setOrderId(order.getId()));
        goodsService.saveBatch(order.getGoodsList());
        return RespBean.success();
    }

    private String getOrderNumber() {
        // 获取今天的年月日
        String currentDate = LocalDate.now().format(formatter);
        StringBuilder orderNumber = new StringBuilder("dd");
        orderNumber.append(currentDate);
        // 查询条件
        List<Order> orderList = orderService.getListByOrderNumberLeftMatching(orderNumber.toString());
        Optional<Order> max = orderList.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId()));
        if (max.isPresent()) {
            // 今天存在订单了
            // 获取订单号的数字
            Matcher matcher = pattern.matcher(max.get().getOrderNumber());
            if (matcher.find()) {
                String suffixStr = matcher.group(1);
                int suffixNumber = Integer.parseInt(suffixStr);
                String number;
                if (suffixNumber >= 999) {
                    number = NumberUtil.decimalFormat("0000", suffixNumber + 1);
                } else {
                    number = NumberUtil.decimalFormat("000", suffixNumber + 1);
                }
                orderNumber.append(number);
            } else {
                // 没有匹配
                log.error("新增订单没有匹配");
                orderNumber.append("001");
            }
        } else {
            // 今天第一条订单
            orderNumber.append("001");
        }
        return orderNumber.toString();
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
        // 删除订单下的货物信息
        goodsService.removeByOrderId(orderId);
        orderService.removeById(orderId);
        return RespBean.success();
    }

    @GetMapping("/detail")
    public RespBean<Order> getOrderDetail(@RequestParam("orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        List<Goods> goodsList = goodsService.getByOrderId(orderId);
        order.setGoodsList(goodsList);
        return RespBean.success(order);
    }

    @PostMapping("/updateOrder")
    public RespBean<Void> updateOrder(@RequestBody Order order) {
        orderService.updateById(order);
        if (CollUtil.isNotEmpty(order.getDelGoodsIdList())) {
            goodsService.removeByIds(order.getDelGoodsIdList());
        }
        order.getGoodsList().stream().filter(item -> Objects.isNull(item.getId())).forEach(item -> item.setOrderId(order.getId()));
        goodsService.saveOrUpdateBatch(order.getGoodsList());
        return RespBean.success();
    }
}
