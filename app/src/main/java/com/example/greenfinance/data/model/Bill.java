package com.example.greenfinance.data.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Bill {
    private long id;
    private long userId;
    private BigDecimal amount;
    private BigDecimal originalAmount;
    private BigDecimal refundAmount;
    private int type; // 1-支出，2-收入
    private long categoryId;
    private Long subCategoryId;
    private String categoryName;
    private String subCategoryName;
    private String merchant;
    private String remark;
    private Date billTime;
    private String paymentMethod;
    private Date createTime;
    private Date updateTime;

    // 构造函数
    public Bill() {
    }

    public Bill(long id, BigDecimal amount, String categoryName, String merchant, Date billTime) {
        this.id = id;
        this.amount = amount;
        this.categoryName = categoryName;
        this.merchant = merchant;
        this.billTime = billTime;
    }

   }