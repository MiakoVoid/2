---
trigger: always_on
---


# 绿芽记账（greenfinance）接口文档
## 一、基础元信息
| 字段     | 取值                                                                  | 说明                                                                 |
|--------|---------------------------------------------------------------------|--------------------------------------------------------------------|
| 项目名    | greenfinance（绿芽记账）                                                  | 个人财务管理安卓应用                                                         |
| 文档版本   | V1.0                                                                | 接口定义稳定版                                                            |
| 基础URL  | 生产：暂定；开发：`http://localhost:8080/api`                                | 接口请求根路径                                                            |
| 认证方式   | JWT Token，请求头携带 `Authorization: Bearer {token}`                     | 除“注册/登录”外所有接口必传                                                    |
| 统一响应格式 | JSON结构，包含`success`（布尔）、`code`（整数）、`message`（字符串）、`data`（对象/数组/Null） | 成功时`success=true`，失败时返回错误原因                                        |
| 核心存储特性 | 图片（头像/分类图标）存储于安卓本地，数据库存“相对路径/资源标识”                                  | 如“file:files/greenfinance/avatars/10001.png”“res:ic_category_food” |
## 七、错误码与补充说明
### 1. 核心错误码
| 错误码 | 含义              | 示例场景             |
|-----|-----------------|------------------|
| 200 | 操作成功            | 账单创建成功           |
| 400 | 请求参数错误          | 密码格式不符合要求        |
| 401 | 未认证（Token过期/无效） | Token过期后调用用户信息接口 |
| 403 | 无权限             | 普通用户调用管理员接口      |
| 404 | 资源不存在           | 访问不存在的账单ID       |
| 500 | 服务端异常           | AI接口调用失败         |

### 2. 本地图片处理规则
- 前端通过`context.getFilesDir()` + 数据库存储的“相对路径”获取图片绝对路径（如`/data/user/0/com.example.greenfinance/files/greenfinance/avatars/10001.png`）
- 系统图标通过`R.drawable.资源名`加载（如`res:ic_category_food`对应`R.drawable.ic_category_food`）



---
title: 默认模块
language_tabs:
- shell: Shell
- http: HTTP
- javascript: JavaScript
- ruby: Ruby
- python: Python
- php: PHP
- java: Java
- go: Go
  toc_footers: []
  includes: []
  search: true
  code_clipboard: true
  highlight_theme: darkula
  headingLevel: 2
  generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

# Authentication

- HTTP Authentication, scheme: bearer

# 账单控制器

## POST 创建账单

POST /bills/add

> Body 请求参数

```json
{
  "id": 0,
  "originalAmount": 0,
  "refundAmount": 0,
  "type": 0,
  "categoryId": 0,
  "subCategoryId": 0,
  "merchant": "string",
  "remark": "string",
  "billTime": "string",
  "paymentMethod": "string",
  "startTime": "string",
  "endTime": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[BillBo](#schemabillbo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## GET 获取账单列表

GET /bills/list

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|page|query|integer| 是 |页码|
|size|query|integer| 是 |每页数量|
|id|query|integer(int64)| 否 |账单ID|
|originalAmount|query|string| 否 |原始金额|
|refundAmount|query|string| 否 |退款金额|
|type|query|integer| 否 |类型：1-支出，2-收入|
|categoryId|query|integer(int64)| 否 |主分类ID（需存在且归属当前用户）|
|subCategoryId|query|integer(int64)| 否 |子分类ID，可为null（若传则需归属对应主分类）|
|merchant|query|string| 否 |商户名称（最长100字符）|
|remark|query|string| 否 |备注（最多500字符）|
|billTime|query|string| 否 |账单时间，格式yyyy-MM-dd HH:mm:ss|
|paymentMethod|query|string| 否 |支付方式（最长50字符）|
|startTime|query|string| 否 |起止时间|
|endTime|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## GET 获取账单详情

GET /bills/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |账单ID|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## DELETE 删除账单

DELETE /bills/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |账单ID|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## PUT 更新账单

PUT /bills/update

> Body 请求参数

```json
{
  "id": 0,
  "originalAmount": 0,
  "refundAmount": 0,
  "type": 0,
  "categoryId": 0,
  "subCategoryId": 0,
  "merchant": "string",
  "remark": "string",
  "billTime": "string",
  "paymentMethod": "string",
  "startTime": "string",
  "endTime": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[BillBo](#schemabillbo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

# 用户登录控制器

## POST 登录

POST /auth/login

> Body 请求参数

```json
{
  "id": 0,
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "avatarPath": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UserBo](#schemauserbo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## POST 注册

POST /auth/register

> Body 请求参数

```json
{
  "id": 0,
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "avatarPath": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UserBo](#schemauserbo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## POST 登出

POST /auth/logout

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

# 子分类控制器

## GET 获取指定分类下的所有子分类

GET /sub-categories

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|categoryId|query|integer| 是 |分类ID|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## POST 创建子分类

POST /sub-categories

> Body 请求参数

```json
{
  "id": 0,
  "categoryId": 0,
  "name": "string",
  "iconIdentifier": "string",
  "sortOrder": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SubCategoryBo](#schemasubcategorybo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## PUT 更新子分类

PUT /sub-categories/update

> Body 请求参数

```json
{
  "id": 0,
  "categoryId": 0,
  "name": "string",
  "iconIdentifier": "string",
  "sortOrder": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SubCategoryBo](#schemasubcategorybo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## DELETE 删除子分类（只有用户自己的子分类才能删除）

DELETE /sub-categories/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |子分类ID|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

# 分类控制器

## GET 获取所有分类（支持按类型筛选）

GET /categories

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |分类类型（可选，1=支出，2=收入）|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## POST 创建主分类

POST /categories

> Body 请求参数

```json
{
  "id": 0,
  "name": "string",
  "iconIdentifier": "string",
  "type": 0,
  "sortOrder": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[CategoryBo](#schemacategorybo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## PUT 更新主分类

PUT /categories/update

> Body 请求参数

```json
{
  "id": 0,
  "name": "string",
  "iconIdentifier": "string",
  "type": 0,
  "sortOrder": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[CategoryBo](#schemacategorybo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

## DELETE 删除主分类（只有用户自己的分类才能删除）

DELETE /categories/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |分类ID|

> 返回示例

> 200 Response

```json
{
  "success": false,
  "code": 0,
  "message": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[Result](#schemaresult)|

# 数据模型

<h2 id="tocS_CommonResponse">CommonResponse</h2>

<a id="schemacommonresponse"></a>
<a id="schema_CommonResponse"></a>
<a id="tocScommonresponse"></a>
<a id="tocscommonresponse"></a>

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||状态码（200=成功，4xx=客户端错误，5xx=服务端错误）|
|message|string|false|none||操作描述信息|
|data|object|false|none||业务数据（成功时返回）|

<h2 id="tocS_LoginRequest">LoginRequest</h2>

<a id="schemaloginrequest"></a>
<a id="schema_LoginRequest"></a>
<a id="tocSloginrequest"></a>
<a id="tocsloginrequest"></a>

```json
{
  "loginId": "test_user",
  "password": "Test123456",
  "rememberMe": false
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|loginId|string|true|none||登录标识（用户名/邮箱/手机号）|
|password|string|true|none||密码（6-20位，含字母和数字）|
|rememberMe|boolean|false|none||是否记住登录（true=7天有效期）|

<h2 id="tocS_RegisterRequest">RegisterRequest</h2>

<a id="schemaregisterrequest"></a>
<a id="schema_RegisterRequest"></a>
<a id="tocSregisterrequest"></a>
<a id="tocsregisterrequest"></a>

```json
{
  "username": "new_user",
  "password": "Test123456",
  "email": "new_user@example.com",
  "phone": 13800138000
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|username|string|true|none||用户名（3-20位，字母/数字/下划线）|
|password|string|true|none||密码（6-20位，含字母和数字）|
|email|string(email)|false|none||邮箱（可选）|
|phone|string|false|none||手机号（可选，11位数字）|

<h2 id="tocS_UserInfoResponse">UserInfoResponse</h2>

<a id="schemauserinforesponse"></a>
<a id="schema_UserInfoResponse"></a>
<a id="tocSuserinforesponse"></a>
<a id="tocsuserinforesponse"></a>

```json
{
  "userId": 123,
  "username": "test_user",
  "email": "test@example.com",
  "phone": 13800138000,
  "avatarFileId": 456,
  "defaultBudget": 5000,
  "themeMode": 1
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||none|
|username|string|false|none||none|
|email|string|false|none||none|
|phone|string|false|none||none|
|avatarFileId|integer|false|none||none|
|defaultBudget|number(float)|false|none||none|
|themeMode|integer|false|none||0=跟随系统，1=浅色，2=深色|

#### 枚举值

|属性|值|
|---|---|
|themeMode|0|
|themeMode|1|
|themeMode|2|

<h2 id="tocS_UpdateUserRequest">UpdateUserRequest</h2>

<a id="schemaupdateuserrequest"></a>
<a id="schema_UpdateUserRequest"></a>
<a id="tocSupdateuserrequest"></a>
<a id="tocsupdateuserrequest"></a>

```json
{
  "email": "updated@example.com",
  "phone": 13900139000,
  "defaultBudget": 6000,
  "themeMode": 1
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|email|string(email)|false|none||none|
|phone|string|false|none||none|
|defaultBudget|number(float)|false|none||none|
|themeMode|integer|false|none||none|

#### 枚举值

|属性|值|
|---|---|
|themeMode|0|
|themeMode|1|
|themeMode|2|

<h2 id="tocS_FileUploadResponse">FileUploadResponse</h2>

<a id="schemafileuploadresponse"></a>
<a id="schema_FileUploadResponse"></a>
<a id="tocSfileuploadresponse"></a>
<a id="tocsfileuploadresponse"></a>

```json
{
  "fileId": 789,
  "filePath": "/app/files/123/avatar.jpg",
  "fileSize": 102400
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|fileId|integer|false|none||none|
|filePath|string|false|none||none|
|fileSize|integer|false|none||文件大小（字节）|

<h2 id="tocS_BillRequest">BillRequest</h2>

<a id="schemabillrequest"></a>
<a id="schema_BillRequest"></a>
<a id="tocSbillrequest"></a>
<a id="tocsbillrequest"></a>

```json
{
  "categoryId": 5,
  "amount": 50,
  "type": 2,
  "billTime": "2025-10-23T12:30:00.000Z",
  "remark": "午餐",
  "location": "XX餐厅",
  "receiptFileId": 1011
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|categoryId|integer|true|none||分类ID（需属于当前用户或系统默认）|
|amount|number(float)|true|none||金额（>0，type区分收支）|
|type|integer|true|none||1=收入，2=支出|
|billTime|string(date-time)|true|none||账单时间（yyyy-MM-dd HH:mm:ss）|
|remark|string|false|none||none|
|location|string|false|none||none|
|receiptFileId|integer|false|none||none|

#### 枚举值

|属性|值|
|---|---|
|type|1|
|type|2|

<h2 id="tocS_BillPageResponse">BillPageResponse</h2>

<a id="schemabillpageresponse"></a>
<a id="schema_BillPageResponse"></a>
<a id="tocSbillpageresponse"></a>
<a id="tocsbillpageresponse"></a>

```json
{
  "total": 120,
  "pageNum": 1,
  "pageSize": 10,
  "list": [
    {
      "billId": 2023,
      "amount": 50,
      "type": 2,
      "typeName": "支出",
      "categoryName": "餐饮美食",
      "billTime": "2025-10-23T12:30:00.000Z"
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|total|integer|false|none||总记录数|
|pageNum|integer|false|none||none|
|pageSize|integer|false|none||none|
|list|[object]|false|none||none|
|» billId|integer|false|none||none|
|» amount|number(float)|false|none||none|
|» type|integer|false|none||none|
|» typeName|string|false|none||none|
|» categoryName|string|false|none||none|
|» billTime|string(date-time)|false|none||none|

<h2 id="tocS_CategoryItem">CategoryItem</h2>

<a id="schemacategoryitem"></a>
<a id="schema_CategoryItem"></a>
<a id="tocScategoryitem"></a>
<a id="tocscategoryitem"></a>

```json
{
  "categoryId": 5,
  "name": "餐饮美食",
  "type": 2,
  "typeName": "支出",
  "icon": "local_cafe",
  "color": null,
  "isDefault": 1
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|categoryId|integer|false|none||none|
|name|string|false|none||none|
|type|integer|false|none||none|
|typeName|string|false|none||none|
|icon|string|false|none||none|
|color|string|false|none||none|
|isDefault|integer|false|none||1=系统默认，0=自定义|

#### 枚举值

|属性|值|
|---|---|
|type|1|
|type|2|
|isDefault|0|
|isDefault|1|

<h2 id="tocS_AddCategoryRequest">AddCategoryRequest</h2>

<a id="schemaaddcategoryrequest"></a>
<a id="schema_AddCategoryRequest"></a>
<a id="tocSaddcategoryrequest"></a>
<a id="tocsaddcategoryrequest"></a>

```json
{
  "name": "健身",
  "type": 2,
  "icon": "fitness_center",
  "color": null
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|name|string|true|none||none|
|type|integer|true|none||none|
|icon|string|false|none||none|
|color|string|false|none||none|

#### 枚举值

|属性|值|
|---|---|
|type|1|
|type|2|

<h2 id="tocS_CategoryKeywordRequest">CategoryKeywordRequest</h2>

<a id="schemacategorykeywordrequest"></a>
<a id="schema_CategoryKeywordRequest"></a>
<a id="tocScategorykeywordrequest"></a>
<a id="tocscategorykeywordrequest"></a>

```json
{
  "keyword": "健身房",
  "weight": 5
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|keyword|string|true|none||none|
|weight|integer|false|none||none|

<h2 id="tocS_BudgetResponse">BudgetResponse</h2>

<a id="schemabudgetresponse"></a>
<a id="schema_BudgetResponse"></a>
<a id="tocSbudgetresponse"></a>
<a id="tocsbudgetresponse"></a>

```json
{
  "budgetId": 50,
  "year": 2025,
  "month": 10,
  "totalBudget": 6000,
  "usedAmount": 2350.5,
  "remainingAmount": 3649.5,
  "usageRate": 39.18
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|budgetId|integer|false|none||none|
|year|integer|false|none||none|
|month|integer|false|none||none|
|totalBudget|number(float)|false|none||none|
|usedAmount|number(float)|false|none||none|
|remainingAmount|number(float)|false|none||none|
|usageRate|number(float)|false|none||使用比例（%）|

<h2 id="tocS_SetBudgetRequest">SetBudgetRequest</h2>

<a id="schemasetbudgetrequest"></a>
<a id="schema_SetBudgetRequest"></a>
<a id="tocSsetbudgetrequest"></a>
<a id="tocssetbudgetrequest"></a>

```json
{
  "year": 2025,
  "month": 11,
  "totalBudget": 6500
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|year|integer|true|none||none|
|month|integer|true|none||none|
|totalBudget|number(float)|true|none||none|

<h2 id="tocS_ExpectedExpenseRequest">ExpectedExpenseRequest</h2>

<a id="schemaexpectedexpenserequest"></a>
<a id="schema_ExpectedExpenseRequest"></a>
<a id="tocSexpectedexpenserequest"></a>
<a id="tocsexpectedexpenserequest"></a>

```json
{
  "categoryId": 8,
  "amount": 3000,
  "planDate": "2025-11-05T00:00:00.000Z",
  "title": "11月房租",
  "description": "XX小区3号楼501房租"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|categoryId|integer|true|none||none|
|amount|number(float)|true|none||none|
|planDate|string(date)|true|none||none|
|title|string|true|none||none|
|description|string|false|none||none|

<h2 id="tocS_ImportResult">ImportResult</h2>

<a id="schemaimportresult"></a>
<a id="schema_ImportResult"></a>
<a id="tocSimportresult"></a>
<a id="tocsimportresult"></a>

```json
{
  "transferId": 15,
  "successCount": 25,
  "failCount": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|transferId|integer|false|none||none|
|successCount|integer|false|none||none|
|failCount|integer|false|none||none|

<h2 id="tocS_OcrParseRequest">OcrParseRequest</h2>

<a id="schemaocrparserequest"></a>
<a id="schema_OcrParseRequest"></a>
<a id="tocSocrparserequest"></a>
<a id="tocsocrparserequest"></a>

```json
{
  "ocrRawText": "XX餐厅 消费45元 2025-10-23 18:45",
  "receiptFileId": 1011
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|ocrRawText|string|true|none||OCR识别的原始文本|
|receiptFileId|integer|false|none||none|

<h2 id="tocS_FinancialAdviceResponse">FinancialAdviceResponse</h2>

<a id="schemafinancialadviceresponse"></a>
<a id="schema_FinancialAdviceResponse"></a>
<a id="tocSfinancialadviceresponse"></a>
<a id="tocsfinancialadviceresponse"></a>

```json
{
  "analysisId": 88,
  "year": 2025,
  "month": 10,
  "content": "本月餐饮支出占比35%，建议控制外卖频率..."
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|analysisId|integer|false|none||none|
|year|integer|false|none||none|
|month|integer|false|none||none|
|content|string|false|none||none|

<h2 id="tocS_ResponseEntityMapObject">ResponseEntityMapObject</h2>

<a id="schemaresponseentitymapobject"></a>
<a id="schema_ResponseEntityMapObject"></a>
<a id="tocSresponseentitymapobject"></a>
<a id="tocsresponseentitymapobject"></a>

```json
{
  "key": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|key|object|false|none||none|

<h2 id="tocS_Result">Result</h2>

<a id="schemaresult"></a>
<a id="schema_Result"></a>
<a id="tocSresult"></a>
<a id="tocsresult"></a>

```json
{
  "success": true,
  "code": 0,
  "message": "string",
  "data": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|success|boolean|false|none||none|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|object|false|none||none|

<h2 id="tocS_UserLoginDTO">UserLoginDTO</h2>

<a id="schemauserlogindto"></a>
<a id="schema_UserLoginDTO"></a>
<a id="tocSuserlogindto"></a>
<a id="tocsuserlogindto"></a>

```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "operation": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|username|string|false|none||none|
|password|string|false|none||none|
|email|string|false|none||none|
|phone|string|false|none||none|
|operation|string|false|none||"login" 或 "register"|

<h2 id="tocS_User">User</h2>

<a id="schemauser"></a>
<a id="schema_User"></a>
<a id="tocSuser"></a>
<a id="tocsuser"></a>

```json
{
  "id": 0,
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "avatarPath": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||用户ID|
|username|string|false|none||用户名（唯一）|
|password|string|false|none||密码（BCrypt加密存储）|
|email|string|false|none||邮箱（唯一）|
|phone|string|false|none||手机号|
|avatarPath|string|false|none||头像本地存储路径（相对路径，如"avatars/10001.png"）|
|status|integer|false|none||状态：0-禁用，1-正常|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|

<h2 id="tocS_CategoryBo">CategoryBo</h2>

<a id="schemacategorybo"></a>
<a id="schema_CategoryBo"></a>
<a id="tocScategorybo"></a>
<a id="tocscategorybo"></a>

```json
{
  "id": 0,
  "name": "string",
  "iconIdentifier": "string",
  "type": 0,
  "sortOrder": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||主分类ID|
|name|string|false|none||分类名称，唯一|
|iconIdentifier|string|false|none||图标标识（res:资源名/file:本地路径）|
|type|integer|false|none||类型：1=支出，2=收入|
|sortOrder|integer|false|none||排序序号，默认0|

<h2 id="tocS_SubCategoryBo">SubCategoryBo</h2>

<a id="schemasubcategorybo"></a>
<a id="schema_SubCategoryBo"></a>
<a id="tocSsubcategorybo"></a>
<a id="tocssubcategorybo"></a>

```json
{
  "id": 0,
  "categoryId": 0,
  "name": "string",
  "iconIdentifier": "string",
  "sortOrder": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||子分类ID|
|categoryId|integer(int64)|false|none||关联主分类ID|
|name|string|false|none||子分类名称，同一主分类下唯一|
|iconIdentifier|string|false|none||图标标识，空则继承主分类|
|sortOrder|integer|false|none||排序序号，默认0|

<h2 id="tocS_BillBo">BillBo</h2>

<a id="schemabillbo"></a>
<a id="schema_BillBo"></a>
<a id="tocSbillbo"></a>
<a id="tocsbillbo"></a>

```json
{
  "id": 0,
  "originalAmount": 0,
  "refundAmount": 0,
  "type": 0,
  "categoryId": 0,
  "subCategoryId": 0,
  "merchant": "string",
  "remark": "string",
  "billTime": "string",
  "paymentMethod": "string",
  "startTime": "string",
  "endTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||账单ID|
|originalAmount|number|false|none||原始金额|
|refundAmount|number|false|none||退款金额|
|type|integer|false|none||类型：1-支出，2-收入|
|categoryId|integer(int64)|false|none||主分类ID（需存在且归属当前用户）|
|subCategoryId|integer(int64)|false|none||子分类ID，可为null（若传则需归属对应主分类）|
|merchant|string|false|none||商户名称（最长100字符）|
|remark|string|false|none||备注（最多500字符）|
|billTime|string|false|none||账单时间，格式yyyy-MM-dd HH:mm:ss|
|paymentMethod|string|false|none||支付方式（最长50字符）|
|startTime|string|false|none||起止时间|
|endTime|string|false|none||none|

<h2 id="tocS_UserBo">UserBo</h2>

<a id="schemauserbo"></a>
<a id="schema_UserBo"></a>
<a id="tocSuserbo"></a>
<a id="tocsuserbo"></a>

```json
{
  "id": 0,
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "avatarPath": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||用户ID|
|username|string|false|none||用户名（唯一）|
|password|string|false|none||密码（BCrypt加密存储）|
|email|string|false|none||邮箱（唯一）|
|phone|string|false|none||手机号|
|avatarPath|string|false|none||头像本地存储路径（相对路径，如"avatars/10001.png"）|
|status|integer|false|none||状态：0-禁用，1-正常|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|


