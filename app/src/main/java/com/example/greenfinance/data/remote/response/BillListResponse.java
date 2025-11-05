package com.example.greenfinance.data.remote.response;

import com.example.greenfinance.data.model.Bill;

import java.util.List;

import lombok.Data;

/**
 * 账单列表响应类
 */
@Data
public class BillListResponse {
    private int total;
    private int pages;
    private int current;
    private int size;
    private List<Bill> list;
}