package com.example.greenfinance.data.model;

import java.util.List;

import lombok.Data;

@Data
public class Category {
    private long id;
    private String name;
    private String iconIdentifier;
    private int type; // 1=支出，2=收入
    private int sortOrder;
    private List<SubCategory> subCategories;

    @Data
    public static class SubCategory {
        private long id;
        private long categoryId;
        private String name;
        private String iconIdentifier;
        private int sortOrder;
    }
}