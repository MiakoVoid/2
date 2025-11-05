package com.example.greenfinance.common.util;

import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.data.model.BillHeader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * 账单日期分组工具类
 * 用于将账单按日期分组并生成带标题的列表
 * 符合项目规范：今天、昨天、MM月dd日 周X
 */
public class BillDateGroupUtil {

    /**
     * 将账单列表按日期分组
     * @param bills 账单列表
     * @return 分组后的对象列表（包含BillHeader和Bill对象）
     */
    public static List<Object> groupBillsByDate(List<Bill> bills) {
        List<Object> groupedItems = new ArrayList<>();
        
        if (bills == null || bills.isEmpty()) {
            return groupedItems;
        }
        
        // 按日期分组
        Map<String, List<Bill>> groupedBills = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        
        for (Bill bill : bills) {
            // 处理空时间情况
            if (bill.getBillTime() == null) {
                continue;
            }
            
            String dateKey = dateFormat.format(bill.getBillTime());
            if (!groupedBills.containsKey(dateKey)) {
                groupedBills.put(dateKey, new ArrayList<>());
            }
            groupedBills.get(dateKey).add(bill);
        }
        
        // 将分组数据添加到列表中
        SimpleDateFormat displayFormat = new SimpleDateFormat("M月d日 E", Locale.getDefault());
        // 获取今天和昨天的日期
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        
        for (Map.Entry<String, List<Bill>> entry : groupedBills.entrySet()) {
            try {
                Date date = dateFormat.parse(entry.getKey());
                Calendar entryDate = Calendar.getInstance();
                entryDate.setTime(date);
                
                String displayDate;
                // 检查是否是今天
                if (isSameDay(entryDate, today)) {
                    displayDate = "今天";
                }
                // 检查是否是昨天
                else if (isSameDay(entryDate, yesterday)) {
                    displayDate = "昨天";
                }
                // 其他日期使用常规格式
                else {
                    displayDate = displayFormat.format(date);
                    // 使用"周"格式而不是"星期"格式
                    if (displayDate.contains("星期")) {
                        displayDate = displayDate.replace("星期", "周");
                    }
                }
                
                groupedItems.add(new BillHeader(displayDate));
                groupedItems.addAll(entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return groupedItems;
    }
    
    /**
     * 检查两个日期是否是同一天
     * @param cal1 第一个日期
     * @param cal2 第二个日期
     * @return 如果是同一天返回true，否则返回false
     */
    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}