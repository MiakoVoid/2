# 分类库文档

## 概述

本文档定义了绿芽记账应用中的分类系统，包括主分类和子分类。分类与子分类之间是一对多的关系，即一个主分类可以拥有多个子分类，但每个子分类只能属于一个主分类。

所有图标均使用Android自带的Material Design图标或者应用内自定义图标。子分类图标不与父分类重复，以区分层级关系。

## 分类结构

### 支出分类

| 主分类名称 | 文件名                           | 图标资源                                | 说明             |
|-------|-------------------------------|-------------------------------------|----------------|
| 餐饮    | ic_category_food.xml          | @drawable/ic_category_food          | 包括日常三餐、饮料、零食等  |
| 交通    | ic_category_transport.xml     | @drawable/ic_category_transport     | 包括公共交通、打车、停车费等 |
| 购物    | ic_category_shopping.xml      | @drawable/ic_category_shopping      | 包括衣物、日用品、电子产品等 |
| 娱乐    | ic_category_entertainment.xml | @drawable/ic_category_entertainment | 包括电影、游戏、聚会等    |
| 医疗    | ic_category_medical.xml       | @drawable/ic_category_medical       | 包括药品、诊疗费等      |
| 教育    | ic_category_education.xml     | @drawable/ic_category_education     | 包括学费、书籍、培训等    |
| 生活    | ic_category_life.xml          | @drawable/ic_category_life          | 包括水电费、通讯费、物业费等 |
| 住房    | ic_category_housing.xml       | @drawable/ic_category_housing       | 包括房租、房贷、装修等    |
| 人情    | ic_category_gift.xml          | @drawable/ic_category_gift          | 包括礼金、红包等       |
| 其他    | ic_category_other.xml         | @drawable/ic_category_other         | 未分类的其他支出       |

#### 支出子分类

| 主分类 | 子分类名称 | 文件名                                   | 图标资源                                        | 说明         |
|-----|-------|---------------------------------------|---------------------------------------------|------------|
| 餐饮  | 快餐    | ic_sub_category_fastfood.xml          | @drawable/ic_sub_category_fastfood          | 快餐、简餐      |
| 餐饮  | 外卖    | ic_sub_category_takeout.xml           | @drawable/ic_sub_category_takeout           | 外卖订餐       |
| 餐饮  | 零食    | ic_sub_category_snack.xml             | @drawable/ic_sub_category_snack             | 零食、小食品     |
| 餐饮  | 饮品    | ic_sub_category_drink.xml             | @drawable/ic_sub_category_drink             | 咖啡、奶茶、果汁等  |
| 餐饮  | 奶茶    | ic_sub_category_milk_tea.xml          | @drawable/ic_sub_category_milk_tea          | 奶茶、果茶等     |
| 交通  | 公共交通  | ic_sub_category_public_transport.xml  | @drawable/ic_sub_category_public_transport  | 公交、地铁等     |
| 交通  | 打车    | ic_sub_category_taxi.xml              | @drawable/ic_sub_category_taxi              | 出租车、网约车等   |
| 交通  | 停车费   | ic_sub_category_parking.xml           | @drawable/ic_sub_category_parking           | 停车相关费用     |
| 交通  | 保养维修  | ic_sub_category_maintenance.xml       | @drawable/ic_sub_category_maintenance       | 车辆保养、维修费用  |
| 购物  | 衣物饰品  | ic_sub_category_clothing.xml          | @drawable/ic_sub_category_clothing          | 服装、鞋帽、配饰等  |
| 购物  | 日用品   | ic_sub_category_daily_necessities.xml | @drawable/ic_sub_category_daily_necessities | 洗护用品、清洁用品等 |
| 购物  | 电子产品  | ic_sub_category_electronics.xml       | @drawable/ic_sub_category_electronics       | 手机、电脑等电子产品 |
| 生活  | 水电费   | ic_sub_category_utilities.xml         | @drawable/ic_sub_category_utilities         | 水费、电费、燃气费等 |
| 生活  | 通讯费   | ic_sub_category_telecom.xml           | @drawable/ic_sub_category_telecom           | 手机费、网费等    |
| 生活  | 物业费   | ic_sub_category_property.xml          | @drawable/ic_sub_category_property          | 小区物业管理费    |
| 娱乐  | 聚会    | ic_sub_category_party.xml             | @drawable/ic_sub_category_party             | 朋友聚餐、聚会活动等 |
| 娱乐  | 电影    | ic_sub_category_movie.xml             | @drawable/ic_sub_category_movie             | 电影院观影      |
| 娱乐  | 旅游    | ic_sub_category_travel.xml            | @drawable/ic_sub_category_travel            | 旅行相关支出     |
| 医疗  | 药品    | ic_sub_category_medicine.xml          | @drawable/ic_sub_category_medicine          | 购买药品       |
| 医疗  | 诊疗    | ic_sub_category_treatment.xml         | @drawable/ic_sub_category_treatment         | 医院诊疗费      |
| 教育  | 学费    | ic_sub_category_tuition.xml           | @drawable/ic_sub_category_tuition           | 学校学费       |
| 教育  | 书籍    | ic_sub_category_books.xml             | @drawable/ic_sub_category_books             | 购买图书       |
| 教育  | 培训    | ic_sub_category_training.xml          | @drawable/ic_sub_category_training          | 培训课程费用     |
| 住房  | 房租    | ic_sub_category_rent.xml              | @drawable/ic_sub_category_rent              | 房屋租赁费用     |
| 住房  | 房贷    | ic_sub_category_mortgage.xml          | @drawable/ic_sub_category_mortgage          | 房屋贷款还款     |
| 住房  | 装修    | ic_sub_category_renovation.xml        | @drawable/ic_sub_category_renovation        | 房屋装修费用     |
| 人情  | 礼金    | ic_sub_category_gift_money.xml        | @drawable/ic_sub_category_gift_money        | 红包礼金等      |
| 人情  | 请客    | ic_sub_category_treat.xml             | @drawable/ic_sub_category_treat             | 请客吃饭等      |

### 收入分类

| 主分类名称 | 文件名                        | 图标资源                             | 说明         |
|-------|----------------------------|----------------------------------|------------|
| 工资    | ic_category_salary.xml     | @drawable/ic_category_salary     | 固定工资收入     |
| 奖金    | ic_category_bonus.xml      | @drawable/ic_category_bonus      | 年终奖、季度奖等   |
| 投资    | ic_category_investment.xml | @drawable/ic_category_investment | 理财、股票等投资收益 |
| 退款    | ic_category_refund.xml     | @drawable/ic_category_refund     | 各种退款收入     |
| 兼职    | ic_category_parttime.xml   | @drawable/ic_category_parttime   | 兼职工作收入     |
| 人情    | ic_category_gift.xml       | @drawable/ic_category_gift       | 收到的礼金等     |
| 其他    | ic_category_other.xml      | @drawable/ic_category_other      | 其他收入来源     |

#### 收入子分类

| 主分类 | 子分类名称 | 文件名                                       | 图标资源                                            | 说明       |
|-----|-------|-------------------------------------------|-------------------------------------------------|----------|
| 工资  | 月薪    | ic_sub_category_monthly_salary.xml        | @drawable/ic_sub_category_monthly_salary        | 每月固定工资   |
| 工资  | 加班费   | ic_sub_category_overtime_pay.xml          | @drawable/ic_sub_category_overtime_pay          | 加班工资收入   |
| 奖金  | 年终奖   | ic_sub_category_year_end_bonus.xml        | @drawable/ic_sub_category_year_end_bonus        | 年终奖金     |
| 奖金  | 绩效奖   | ic_sub_category_performance_bonus.xml     | @drawable/ic_sub_category_performance_bonus     | 绩效奖金     |
| 投资  | 理财收益  | ic_sub_category_financial_income.xml      | @drawable/ic_sub_category_financial_income      | 理财产品收益   |
| 投资  | 股票收益  | ic_sub_category_stock_income.xml          | @drawable/ic_sub_category_stock_income          | 股票投资收益   |
| 兼职  | 设计兼职  | ic_sub_category_design_freelance.xml      | @drawable/ic_sub_category_design_freelance      | 设计类兼职收入  |
| 兼职  | 咨询兼职  | ic_sub_category_consulting_freelance.xml  | @drawable/ic_sub_category_consulting_freelance  | 咨询类兼职收入  |
| 兼职  | 翻译兼职  | ic_sub_category_translation_freelance.xml | @drawable/ic_sub_category_translation_freelance | 翻译类兼职收入  |
| 人情  | 礼金    | ic_sub_category_gift_money.xml            | @drawable/ic_sub_category_gift_money            | 收到的红包礼金等 |
| 其他  | 中奖收入  | ic_sub_category_lottery_winnings.xml      | @drawable/ic_sub_category_lottery_winnings      | 彩票、抽奖等   |
| 其他  | 意外收入  | ic_sub_category_other_income.xml          | @drawable/ic_sub_category_other_income          | 其他意外收入   |

## 使用说明

1. 所有图标资源均存储在 `res/drawable/` 目录下
2. 主分类图标文件名格式为 `ic_category_*.xml`
3. 子分类图标文件名格式为 `ic_sub_category_*.xml`
4. 在数据库中，图标通过资源标识符引用，格式为 `res:资源名`，例如 `res:ic_category_food`
5. 子分类图标不与父分类重复，以区分层级关系

## 图标设计规范

1. 尺寸：24dp x 24dp
2. 颜色：使用 `@android:color/white` 填充，通过 tint 设置颜色
3. 风格：遵循 Material Design 设计规范
4. 命名：使用语义化命名，便于理解和维护

## 数据库设计说明

分类数据存储在数据库中，主要包括以下表：

1. `category` 表：存储主分类信息
2. `sub_category` 表：存储子分类信息
3. `category_keyword` 表：存储分类关键词，用于智能分类

### 主分类表 (category)

| 字段名             | 类型           | 说明                                  |
|-----------------|--------------|-------------------------------------|
| id              | bigint       | 主分类ID                               |
| user_id         | bigint       | 用户ID（null表示系统默认分类）                  |
| name            | varchar(50)  | 分类名称                                |
| icon_identifier | varchar(100) | 图标标识：系统图标用"res:资源名"（如"res:ic_food"） |
| type            | tinyint      | 类型：1-支出，2-收入                        |
| sort_order      | int          | 排序序号（升序排列）                          |
| create_time     | datetime     | 创建时间                                |
| update_time     | datetime     | 更新时间                                |

### 子分类表 (sub_category)

| 字段名             | 类型           | 说明                 |
|-----------------|--------------|--------------------|
| id              | bigint       | 子分类ID              |
| category_id     | bigint       | 关联主分类ID            |
| user_id         | bigint       | 用户ID（null表示系统默认分类） |
| name            | varchar(50)  | 子分类名称              |
| icon_identifier | varchar(100) | 图标标识（为空时继承主分类图标）   |
| sort_order      | int          | 排序序号（升序排列）         |
| create_time     | datetime     | 创建时间               |
| update_time     | datetime     | 更新时间               |

### 分类关键词表 (category_keyword)

| 字段名             | 类型           | 说明                 |
|-----------------|--------------|--------------------|
| id              | bigint       | 关键词ID              |
| category_id     | bigint       | 关联主分类ID            |
| sub_category_id | bigint       | 关联子分类ID            |
| user_id         | bigint       | 所属用户ID（0表示系统默认关键词） |
| keyword         | varchar(100) | 关键词（如商户名、商品名）      |
| create_time     | datetime     | 创建时间               |
| update_time     | datetime     | 更新时间               |