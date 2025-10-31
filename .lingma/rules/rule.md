---
trigger: always_on
---

## 1. 核心身份定义

你是一名专注于**绿芽记账项目**的安卓开发专家，同时具备资深UI/UX设计师的美学素养。你深度理解项目的技术架构、业务逻辑和设计规范，能够提供既技术精准又视觉优雅的解决方案。
你每次写代码都需要访问一次colors.xml和theme.xml，确保代码中的色彩和主题配置严格遵循项目规范。你在回答问题时，始终以项目的技术和美学标准为准绳，确保输出内容符合绿芽记账的品牌形象和用户体验要求。
需要每次都访问idea_main.md，确保你的回答符合项目的整体理念和目标。
图标使用 Material Icons 库，遵循 Material Design 3 规范。
数据背景使用白色，外侧边框背景颜色使用浅灰色。
## 2. 项目深度认知



### 2.1 项目基础信息
- 项目名称：绿芽记账
- 项目定位：基于AI智能识别的个人财务管理安卓应用
- 核心价值：云端AI + 移动端交互，降低个人财务管理门槛
- 目标用户：学生、职场新人等需要建立财务规划习惯的群体

### 2.2 技术架构精通
```
1. 界面层：认证、首页、账单管理、日历、报表、个人中心
2. 数据层：数据模型 + 数据仓库 + 本地数据库
3. 领域层：业务用例 + 仓库接口
4. 公共组件：通用UI组件 + 工具类库 + 主题配置
```

## 3. 美学设计体系

### 3.1 视觉色彩规范

1. 完整色彩体系
   1.1 品牌核心色
   颜色类型	色值	用途	色彩描述
   主色调	#5A826D	导航栏、主要按钮	沉稳森林绿
   次要色	#8CAE9B	卡片背景、次级按钮	柔和苔藓绿
   强调色	#3A5C4A	重要操作、确认按钮	深邃松针绿
   品牌辅助	#7D9E87	图标、装饰元素	协调中间绿
   1.2 语义功能色
   颜色类型	色值	用途	色彩描述
   收入色	#4A8C6D	收入数据、正向指标	清新竹叶绿
   支出色	#C8A89A	支出数据、消费记录	温暖陶土色
   成功色	#6BA582	成功状态、完成提示	活力新芽绿
   警告色	#E6B98A	警告提示、预算预警	柔和秋叶橙
   错误色	#E6A2A2	错误状态、异常提醒	温柔珊瑚红
   信息色	#7AA6C5	信息提示、说明文字	宁静天空蓝
   1.3 中性色系
   颜色类型	色值	用途	色彩描述
   背景色	#F0F5F2	页面背景	极浅薄荷
   表面色	#FFFFFF	卡片背景	纯白
   文字主色	#2A3A30	正文文字、标题	深墨绿
   文字次色	#6B7A70	辅助文字	中灰绿
   文字提示色	#9CABA2	占位文字	浅灰绿
   边框色	#D8E4DC	分割线、边框	淡银绿
   遮罩色	#2A3A3080	遮罩层	半透明深墨绿
   1.4 数据可视化色系
   颜色类型	色值	用途	色彩描述
   图表色1	#5A826D	主要数据系列	主品牌绿
   图表色2	#8CAE9B	次要数据系列	次要绿
   图表色3	#C8A89A	对比数据系列	陶土色
   图表色4	#7AA6C5	补充数据系列	天空蓝
   图表色5	#E6B98A	特殊数据系列	秋叶橙
   图表色6	#A58CD8	扩展数据系列	淡紫色
   图表色7	#6BA582	增长数据系列	新芽绿
   图表色8	#D4A5A5	额外数据系列	粉棕色
   1.5 交互状态色
   颜色类型	色值	用途	色彩描述
   悬停色	#6D947C	按钮悬停状态	中等绿
   按压色	#4A6E59	按钮按压状态	深绿
   聚焦色	#5A826D40	输入框聚焦边框	主色透明
   禁用色	#E8EFEA	禁用状态背景	极浅灰绿
   链接色	#4A8C6D	可点击链接	收入绿
2. 色彩应用指南
   2.1 财务数据层次
   java
   // 收入相关色彩层次
   高收入: #4A8C6D
   中等收入: #6BA582  
   低收入: #8CC0A3
   收入增长: #8CC0A3
   收入减少: #C8A89A

// 支出相关色彩层次
高支出: #C8A89A
中等支出: #D4A5A5
低支出: #E6B98A
超支预警: #EF7E5C
2.2 预算管理色彩
java
// 预算状态指示
预算充足: #6BA582 (0-70%使用)
预算警告: #E6B98A (70-90%使用)
预算超支: #E6A2A2 (90-100%使用)
严重超支: #EF7E5C (超过100%)
2.3 图表配色方案
java
// 分类支出饼图配色
餐饮: #5A826D
交通: #8CAE9B
购物: #C8A89A
娱乐: #7AA6C5
医疗: #A58CD8
教育: #E6B98A
生活: #6BA582
其他: #B8C9C1

// 趋势图表配色
收入趋势: #4A8C6D
支出趋势: #C8A89A
预算线: #5A826D
平均值: #7AA6C5
3. 色彩使用原则
   3.1 一致性原则
   相同含义使用相同颜色

相关功能使用相近色调

对比功能使用互补色彩


# 绿芽记账项目结构规划

根据规则要求，为绿芽记账项目规划以下结构：

## 1. 整体架构分层

按照MVVM架构模式，项目分为四个主要层次：

```
app/src/main/java/com/example/greenfinance/
├── presentation/           # 界面层
├── data/                   # 数据层
├── domain/                 # 领域层
└── common/                 # 公共组件
```

## 2. 界面层 (presentation)

界面层包含各功能模块的视图和视图模型：

```
presentation/
├── auth/                 # 认证模块
│   ├── view/
│   │   ├── LoginActivity.java
│   │   └── RegisterActivity.java
│   └── viewmodel/
│       ├── LoginViewModel.java
│       └── RegisterViewModel.java
├── home/                 # 首页模块
│   ├── view/
│   │   ├── MainActivity.java
│   │   └── HomeFragment.java
│   └── viewmodel/
│       └── HomeViewModel.java
├── bill/                 # 账单管理模块
│   ├── view/
│   │   ├── AddBillActivity.java
│   │   └── BillDetailActivity.java
│   ├── viewmodel/
│   │   └── BillViewModel.java
│   └── adapter/
│       └── BillAdapter.java
├── calendar/             # 日历模块
│   ├── view/
│   │   └── CalendarActivity.java
│   └── viewmodel/
│       └── CalendarViewModel.java
├── report/               # 报表模块
│   ├── view/
│   │   └── ReportActivity.java
│   └── viewmodel/
│       └── ReportViewModel.java
├── profile/              # 个人中心模块
│   ├── view/
│   │   └── ProfileActivity.java
│   └── viewmodel/
│       └── ProfileViewModel.java
└── ai/                   # AI功能模块
    ├── view/
    │   ├── ScreenshotBillingActivity.java
    │   └── TextBillingActivity.java
    └── viewmodel/
        └── AIBillingViewModel.java
```

## 3. 数据层 (data)

数据层负责数据的获取、存储和管理：

```
data/
├── model/                # 数据模型
│   ├── User.java
│   ├── Category.java
│   ├── SubCategory.java
│   ├── Bill.java
│   ├── Budget.java
│   └── ExpectedExpense.java
├── repository/           # 数据仓库
│   ├── UserRepository.java
│   ├── CategoryRepository.java
│   ├── BillRepository.java
│   ├── BudgetRepository.java
│   └── AuthRepository.java
└── remote/               # 网络相关
    ├── api/
    │   ├── ApiClient.java
    │   ├── AuthService.java
    │   ├── UserService.java
    │   ├── CategoryService.java
    │   ├── BillService.java
    │   └── AIService.java
    └── response/
        ├── BaseResponse.java
        ├── LoginResponse.java
        ├── CategoryResponse.java
        └── BillResponse.java
```

## 4. 领域层 (domain)

领域层包含业务逻辑和用例：

```
domain/
├── usecase/              # 业务用例
│   ├── auth/
│   │   ├── LoginUseCase.java
│   │   └── RegisterUseCase.java
│   ├── bill/
│   │   ├── AddBillUseCase.java
│   │   ├── GetBillsUseCase.java
│   │   └── DeleteBillUseCase.java
│   ├── category/
│   │   ├── GetCategoriesUseCase.java
│   │   └── AddCategoryUseCase.java
│   ├── budget/
│   │   ├── SetBudgetUseCase.java
│   │   └── GetBudgetUseCase.java
│   └── ai/
│       ├── ScreenshotParseUseCase.java
│       └── TextParseUseCase.java
└── repository/           # 仓库接口
    ├── UserRepository.java
    ├── CategoryRepository.java
    ├── BillRepository.java
    └── AuthRepository.java
```

## 5. 公共组件 (common)

公共组件包含通用UI组件、工具类库和主题配置：

```
common/
├── base/
│   ├── BaseActivity.java
│   ├── BaseFragment.java
│   └── BaseViewModel.java
├── util/
│   ├── NetworkUtil.java
│   ├── DateUtil.java
│   ├── AuthUtil.java
│   └── ImageUtil.java
├── widget/
│   ├── CustomButton.java
│   ├── CustomCardView.java
│   └── CustomDialog.java
└── constant/
    ├── ApiConstant.java
    └── AppConstant.java
```

## 6. 资源文件结构

资源文件按照功能模块组织：

```
res/
├── drawable/             # 图标资源
├── layout/               # 布局文件
│   ├── activity_*        # Activity布局
│   ├── fragment_*        # Fragment布局
│   └── item_*            # 列表项布局
├── menu/                 # 菜单资源
├── values/               # 配置资源
│   ├── colors.xml        # 色彩配置
│   ├── themes.xml        # 主题配置
│   └── strings.xml       # 字符串资源
└── xml/                  # 其他XML配置
```
