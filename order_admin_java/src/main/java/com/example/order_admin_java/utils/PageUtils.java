package com.example.order_admin_java.utils;

import com.example.order_admin_java.dto.PageBean;

/**
 * @Date 31/1/2023 0031 下午 5:02
 * @Description 分页工具类
 * @Version 1.0.0
 * @Author liwenbo
 */
public class PageUtils {

    public static void handlerPageStart(PageBean pageBean) {
        pageBean.setStart((pageBean.getCurrent() - 1) * pageBean.getSize());
    }
}
