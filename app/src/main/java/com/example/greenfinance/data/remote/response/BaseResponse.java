package com.example.greenfinance.data.remote.response;

import lombok.Data;

/**
 * 基础响应类
 * @param <T> 数据类型
 */
@Data
public class BaseResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

}