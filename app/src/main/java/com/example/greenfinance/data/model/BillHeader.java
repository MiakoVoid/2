package com.example.greenfinance.data.model;

public class BillHeader {
    private String dateText; // 例如："10月25日 周三"

    public BillHeader(String dateText) {
        this.dateText = dateText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}