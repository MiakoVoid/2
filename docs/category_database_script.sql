/*
 分类数据库脚本
 实现分类信息从数据库获取
*/

-- 插入主分类数据 (支出)
INSERT INTO category (id, user_id, name, icon_identifier, type, sort_order) VALUES
(1, NULL, '餐饮', 'res:ic_category_food', 1, 1),
(2, NULL, '交通', 'res:ic_category_transport', 1, 2),
(3, NULL, '购物', 'res:ic_category_shopping', 1, 3),
(4, NULL, '娱乐', 'res:ic_category_entertainment', 1, 4),
(5, NULL, '医疗', 'res:ic_category_medical', 1, 5),
(6, NULL, '教育', 'res:ic_category_education', 1, 6),
(7, NULL, '生活', 'res:ic_category_life', 1, 7),
(8, NULL, '住房', 'res:ic_category_housing', 1, 8),
(9, NULL, '人情', 'res:ic_category_gift', 1, 9),
(10, NULL, '其他', 'res:ic_category_other', 1, 10);

-- 插入主分类数据 (收入)
INSERT INTO category (id, user_id, name, icon_identifier, type, sort_order) VALUES
(11, NULL, '工资', 'res:ic_category_salary', 2, 1),
(12, NULL, '奖金', 'res:ic_category_bonus', 2, 2),
(13, NULL, '投资', 'res:ic_category_investment', 2, 3),
(14, NULL, '退款', 'res:ic_category_refund', 2, 4),
(15, NULL, '兼职', 'res:ic_category_parttime', 2, 5),
(16, NULL, '人情', 'res:ic_category_gift', 2, 6),
(17, NULL, '其他', 'res:ic_category_other', 2, 7);

-- 插入子分类数据 (支出)
INSERT INTO sub_category (id, category_id, user_id, name, icon_identifier, sort_order) VALUES
(1, 1, NULL, '快餐', 'res:ic_sub_category_fastfood', 1),
(2, 1, NULL, '外卖', 'res:ic_sub_category_takeout', 2),
(3, 1, NULL, '零食', 'res:ic_sub_category_snack', 3),
(4, 1, NULL, '饮品', 'res:ic_sub_category_drink', 4),
(5, 1, NULL, '奶茶', 'res:ic_sub_category_milk_tea', 5),

(6, 2, NULL, '公共交通', 'res:ic_sub_category_public_transport', 1),
(7, 2, NULL, '打车', 'res:ic_sub_category_taxi', 2),
(8, 2, NULL, '停车费', 'res:ic_sub_category_parking', 3),
(9, 2, NULL, '保养维修', 'res:ic_sub_category_maintenance', 4),

(10, 3, NULL, '衣物饰品', 'res:ic_sub_category_clothing', 1),
(11, 3, NULL, '日用品', 'res:ic_sub_category_daily_necessities', 2),
(12, 3, NULL, '电子产品', 'res:ic_sub_category_electronics', 3),

(13, 7, NULL, '水电费', 'res:ic_sub_category_utilities', 1),
(14, 7, NULL, '通讯费', 'res:ic_sub_category_telecom', 2),
(15, 7, NULL, '物业费', 'res:ic_sub_category_property', 3),

(16, 4, NULL, '聚会', 'res:ic_sub_category_party', 1),
(17, 4, NULL, '电影', 'res:ic_sub_category_movie', 2),
(18, 4, NULL, '旅游', 'res:ic_sub_category_travel', 3),

(19, 5, NULL, '药品', 'res:ic_sub_category_medicine', 1),
(20, 5, NULL, '诊疗', 'res:ic_sub_category_treatment', 2),

(21, 6, NULL, '学费', 'res:ic_sub_category_tuition', 1),
(22, 6, NULL, '书籍', 'res:ic_sub_category_books', 2),
(23, 6, NULL, '培训', 'res:ic_sub_category_training', 3),

(24, 8, NULL, '房租', 'res:ic_sub_category_rent', 1),
(25, 8, NULL, '房贷', 'res:ic_sub_category_mortgage', 2),
(26, 8, NULL, '装修', 'res:ic_sub_category_renovation', 3),

(27, 9, NULL, '礼金', 'res:ic_sub_category_gift_money', 1),
(28, 9, NULL, '请客', 'res:ic_sub_category_treat', 2);

-- 插入子分类数据 (收入)
INSERT INTO sub_category (id, category_id, user_id, name, icon_identifier, sort_order) VALUES
(29, 11, NULL, '月薪', 'res:ic_sub_category_monthly_salary', 1),
(30, 11, NULL, '加班费', 'res:ic_sub_category_overtime_pay', 2),

(31, 12, NULL, '年终奖', 'res:ic_sub_category_year_end_bonus', 1),
(32, 12, NULL, '绩效奖', 'res:ic_sub_category_performance_bonus', 2),

(33, 13, NULL, '理财收益', 'res:ic_sub_category_financial_income', 1),
(34, 13, NULL, '股票收益', 'res:ic_sub_category_stock_income', 2),

(35, 15, NULL, '设计兼职', 'res:ic_sub_category_design_freelance', 1),
(36, 15, NULL, '咨询兼职', 'res:ic_sub_category_consulting_freelance', 2),
(37, 15, NULL, '翻译兼职', 'res:ic_sub_category_translation_freelance', 3),

(38, 16, NULL, '礼金', 'res:ic_sub_category_gift_money', 1),

(39, 17, NULL, '中奖收入', 'res:ic_sub_category_lottery_winnings', 1),
(40, 17, NULL, '意外收入', 'res:ic_sub_category_other_income', 2);

