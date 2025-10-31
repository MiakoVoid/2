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

## 7. 设计规范遵循

### 7.1 色彩规范
严格遵循[colors.xml](file:///D:/DevelopmentTool/Android/Androidprogram/GreenFinance/app/src/main/res/values/colors.xml)中定义的色彩体系：
- 品牌核心色：主色调#5A826D、次要色#8CAE9B、强调色#3A5C4A
- 语义功能色：收入色#4A8C6D、支出色#C8A89A等
- 中性色系：背景色#F0F5F2、文字色#2A3A30等

### 7.2 主题规范
遵循[themes.xml](file:///D:/DevelopmentTool/Android/Androidprogram/GreenFinance/app/src/main/res/values/themes.xml)中定义的主题配置，使用Material Design 3设计语言。

### 7.3 布局规范
- 采用卡片式布局
- 确保充足留白（8dp/16dp圆角）
- 提供微交互反馈

## 8. 技术实现规范

### 8.1 架构规范
- 严格遵循MVVM架构模式
- 界面层只负责UI展示和用户交互
- 数据层负责数据获取和存储
- 领域层封装核心业务逻辑
- 公共组件提供可复用功能

### 8.2 代码规范
- 使用Java语言开发
- 遵循Material Design 3设计规范
- 严格按照分层架构组织代码
- 统一命名规范

### 8.3 功能模块规范
- 认证模块：处理用户登录注册
- 首页模块：展示财务概览和核心功能
- 账单管理：处理账单的增删改查
- 日历视图：按时间维度展示消费记录
- 报表分析：提供数据可视化分析
- 个人中心：用户设置和数据管理
- AI功能：智能记账相关功能

## 9. 核心功能页面

### 9.1 登录页面
采用居中卡片式布局，提供注册入口和记住密码功能。

### 9.2 首页
包含顶部导航栏、财务概览卡片、预算管理模块和所有账单列表。

### 9.3 预算管理页面
- 支持设置月度预算金额
- 增加、编辑和删除预算项
- 显示预算项列表

### 9.4 底部导航栏设计
```
[首页] [日历] [+] [报表] [我的]
```

### 9.5 账单详情页面
展示账单详细信息，支持编辑、删除和退款操作。

### 9.6 添加账单页面
提供三种记账方式：手动选择记账、截图记账、文本记账。

### 9.7 智能记账确认页面
展示AI识别结果的预览，允许用户修改所有识别字段。

### 9.8 日历页面
展示月度日历视图，标记每日消费情况。

### 9.9 报表页面
提供多维度财务分析，包括支出趋势图、构成分析、分类排行。

### 9.10 个人中心页面
展示用户信息、数据统计和功能入口。

## 10. AI智能功能

### 10.1 OCR识别流程
图像获取 → 文字提取 → 数据解析 → 智能分类 → 结果返回 → 用户确认

### 10.2 文本解析流程
文本输入 → AI解析 → 结果预览 → 用户确认 → 保存

### 10.3 条件性分类创建机制
- AI识别时严格限制在现有分类库范围内
- 仅当用户明确修改分类为新分类时触发自动创建