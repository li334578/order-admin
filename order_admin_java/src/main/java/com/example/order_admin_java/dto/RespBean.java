package com.example.order_admin_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 31/1/2023 0031 下午 3:05
 * @Description 同一结果封装类
 * @Version 1.0.0
 * @Author liwenbo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * msg
     */
    private String msg;
    /**
     * 数据
     */
    private T obj;

    public static <T> RespBean<T> success() {
        return new RespBean<>(1, "success", null);
    }

    public static <T> RespBean<T> success(T data) {
        return new RespBean<>(1, "success", data);
    }

    public static <T> RespBean<T> error() {
        return new RespBean<>(2, "error", null);
    }
}
