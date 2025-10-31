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


## 二、用户认证模块（Auth Module）
### 1. 用户注册
- **接口标识**：地址=`/auth/register`，方法=POST
- 描述：注册账号，只需要username和password,暂时不考虑email和phone的验证功能。密码6-20位，含字母+数字。
  - **请求参数（JSON）**：
    | 参数名     | 类型   | 必传 | 说明                          | 示例值               |
    |------------|--------|------|-------------------------------|----------------------|
    | username   | String | 是   | 3-20位，含字母/数字/下划线    | "zhangsan123"        |
    | password   | String | 是   | 6-20位，含字母+数字           | "Zhang@123456"       |
    | email      | String | 否   | 符合邮箱格式                   | "zhangsan@xxx.com"   |
    | phone      | String | 否   | 11位数字                      | "13800138000"        |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "注册成功",
    "data": {
      "userId": 10001,
      "username": "zhangsan123",
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
  }
  ```

### 2. 用户登录
- **接口标识**：地址=`/auth/login`，方法=POST
- 描述：暂时只支持用户名+密码登录。
- **请求参数（JSON）**：
  | 参数名     | 类型    | 必传 | 说明                          | 示例值               |
  |------------|---------|------|-------------------------------|----------------------|
  | username   | String  | 是   | 用户名/邮箱/手机号            | "zhangsan123"        |
  | password   | String  | 是   | 登录密码                      | "Zhang@123456"       |
  | rememberMe | Boolean | 否   | 表示前端记住密码| true                 |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "登录成功",
    "data": {
      "userId": 10001,
      "username": "zhangsan123",
      "avatarPath": "files/greenfinance/avatars/10001_1698000000.png",
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
  }
  ```

### 3. 用户登出
- **接口标识**：地址=`/auth/logout`，方法=POST
- **请求参数**：仅需请求头`Authorization: Bearer {token}`
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "登出成功",
    "data": null
  }
  ```


## 三、用户信息模块（User Profile Module）
### 1. 获取用户信息
- **接口标识**：地址=`/user/profile`，方法=GET
- **请求参数**：请求头`Authorization: Bearer {token}`
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "success",
    "data": {
      "userId": 10001,
      "username": "zhangsan123",
      "email": "zhangsan@xxx.com",
      "phone": "13800138000",
      "avatarPath": "files/greenfinance/avatars/10001_1698000000.png",
      "createTime": "2023-10-23 14:30:00"
    }
  }
  ```

### 2. 更新用户信息
- **接口标识**：地址=`/user/profile`，方法=PUT
- **请求参数（JSON）**：
  | 参数名       | 类型   | 必传 | 说明                          | 示例值               |
  |--------------|--------|------|-------------------------------|----------------------|
  | email        | String | 否   | 新邮箱，需唯一且符合格式      | "new_zhangsan@xxx.com"|
  | phone        | String | 否   | 新手机号，11位数字            | "13900139000"        |
  | avatarPath   | String | 否   | 新头像本地相对路径（前端先存图）| "files/greenfinance/avatars/10001_1698100000.png" |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "用户信息更新成功",
    "data": null
  }
  ```

### 3. 修改密码
- **接口标识**：地址=`/user/password`，方法=PUT
- **请求参数（JSON）**：
  | 参数名       | 类型   | 必传 | 说明                          | 示例值               |
  |--------------|--------|------|-------------------------------|----------------------|
  | oldPassword  | String | 是   | 原密码                        | "Zhang@123456"       |
  | newPassword  | String | 是   | 新密码，6-20位含字母+数字     | "Zhang@654321"       |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "密码修改成功，请重新登录",
    "data": null
  }
  ```


## 四、分类管理模块（Category Module）
### 1. 获取所有分类（含子分类）
- **接口标识**：地址=`/categories`，方法=GET
- **请求参数**：请求头`Authorization: Bearer {token}`，查询参数`type`（可选，1=支出，2=收入）
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "name": "餐饮",
        "iconIdentifier": "res:ic_category_food",
        "type": 1,
        "sortOrder": 1,
        "subCategories": [
          {
            "id": 1,
            "name": "快餐",
            "iconIdentifier": null,
            "sortOrder": 1
          },
          {
            "id": 2,
            "name": "奶茶",
            "iconIdentifier": "file:files/greenfinance/icons/milk_tea.png",
            "sortOrder": 2
          }
        ]
      }
    ]
  }
  ```

### 2. 创建主分类
- **接口标识**：地址=`/categories`，方法=POST
- **请求参数（JSON）**：
  | 参数名         | 类型   | 必传 | 说明                          | 示例值               |
  |----------------|--------|------|-------------------------------|----------------------|
  | name           | String | 是   | 主分类名称，唯一              | "数码"               |
  | iconIdentifier | String | 是   | 图标标识（res:资源名/file:本地路径）| "res:ic_category_digital" |
  | type           | Integer| 是   | 1=支出，2=收入                | 1                    |
  | sortOrder      | Integer| 否   | 排序序号，默认0               | 3                    |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "主分类创建成功",
    "data": {
      "id": 9,
      "name": "数码",
      "iconIdentifier": "res:ic_category_digital",
      "type": 1
    }
  }
  ```

### 3. 创建子分类
- **接口标识**：地址=`/sub-categories`，方法=POST
- **请求参数（JSON）**：
  | 参数名         | 类型   | 必传 | 说明                          | 示例值               |
  |----------------|--------|------|-------------------------------|----------------------|
  | categoryId     | Long   | 是   | 关联主分类ID                  | 9                    |
  | name           | String | 是   | 子分类名称，同一主分类下唯一  | "手机配件"           |
  | iconIdentifier | String | 否   | 图标标识，空则继承主分类      | "file:files/greenfinance/icons/phone_part.png" |
  | sortOrder      | Integer| 否   | 排序序号，默认0               | 1                    |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "子分类创建成功",
    "data": {
      "id": 21,
      "categoryId": 9,
      "name": "手机配件",
      "iconIdentifier": "file:files/greenfinance/icons/phone_part.png"
    }
  }
  ```

### 4. 分类关键词管理（AI识别用）
- **接口标识**：地址=`/category-keywords`，方法=POST（新增）/GET（查询）/DELETE（删除）
- **新增关键词请求参数（JSON）**：
  | 参数名         | 类型   | 必传 | 说明                          | 示例值               |
  |----------------|--------|------|-------------------------------|----------------------|
  | categoryId     | Long   | 是   | 主分类ID                      | 1                    |
  | subCategoryId  | Long   | 否   | 子分类ID，可为null            | 1                    |
  | keyword        | String | 是   | 关键词（如商户名）            | "麦当劳"             |
- **响应示例（新增）**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "关键词添加成功",
    "data": {
      "id": 101,
      "keyword": "麦当劳",
      "categoryId": 1,
      "subCategoryId": 1
    }
  }
  ```


## 五、账单管理模块（Bill Module）
### 1. 创建账单
- **接口标识**：地址=`/bills`，方法=POST
- **请求参数（JSON）**：
  | 参数名           | 类型      | 必传 | 说明                          | 示例值               |
  |------------------|-----------|------|-------------------------------|----------------------|
  | originalAmount   | BigDecimal| 是   | 原始金额（正数）              | 50.00                |
  | refundAmount     | BigDecimal| 否   | 退款金额，默认0，不超原始金额 | 10.00                |
  | type             | Integer   | 是   | 1=支出，2=收入                | 1                    |
  | categoryId       | Long      | 是   | 主分类ID                      | 1                    |
  | subCategoryId    | Long      | 否   | 子分类ID，可为null            | 1                    |
  | merchant         | String    | 否   | 商户名称                      | "麦当劳（科技园店）" |
  | remark           | String    | 否   | 备注，最多500字               | "早餐：汉堡+可乐"    |
  | billTime         | String    | 是   | 账单时间，格式yyyy-MM-dd HH:mm:ss | "2023-10-25 08:30:00"|
  | paymentMethod    | String    | 否   | 支付方式                      | "微信支付"           |
- **备注**：后端自动计算`amount=originalAmount-refundAmount`
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "账单创建成功",
    "data": {
      "id": 10001
    }
  }
  ```

### 2. 获取账单列表
- **接口标识**：地址=`/bills`，方法=GET
- **请求参数**：请求头`Authorization: Bearer {token}`，查询参数（可选）：
  | 参数名           | 类型      | 说明                          | 示例值               |
  |------------------|-----------|-------------------------------|----------------------|
  | page             | Integer   | 页码，默认1                   | 1                    |
  | size             | Integer   | 每页条数，默认20              | 20                   |
  | startTime        | String    | 开始日期，格式yyyy-MM-dd      | "2023-10-01"         |
  | endTime          | String    | 结束日期，格式yyyy-MM-dd      | "2023-10-31"         |
  | categoryId       | Long      | 主分类ID                      | 1                    |
  | type             | Integer   | 1=支出，2=收入                | 1                    |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "success",
    "data": {
      "total": 120,
      "pages": 6,
      "current": 1,
      "size": 20,
      "list": [
        {
          "id": 10001,
          "amount": 40.00,
          "originalAmount": 50.00,
          "refundAmount": 10.00,
          "type": 1,
          "categoryName": "餐饮",
          "subCategoryName": "快餐",
          "merchant": "麦当劳（科技园店）",
          "remark": "早餐：汉堡+可乐",
          "billTime": "2023-10-25 08:30:00",
          "paymentMethod": "微信支付"
        }
      ]
    }
  }
  ```


## 六、智能记账模块（AI Billing Module）
### 1. OCR文本解析（截图记账）
- **接口标识**：地址=`/ai/ocr-parse`，方法=POST
- **请求参数（JSON）**：
  | 参数名       | 类型   | 必传 | 说明                          | 示例值               |
  |--------------|--------|------|-------------------------------|----------------------|
  | ocrText      | String | 是   | Google ML Kit识别的文本内容   | "微信支付 收款方：喜茶(科技园店) 金额：¥35.50 时间：2023-10-25 14:30:00" |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "OCR解析成功",
    "data": {
      "amount": 35.50,
      "type": 1,
      "categoryId": 1,
      "categoryName": "餐饮",
      "subCategoryId": 2,
      "subCategoryName": "奶茶",
      "merchant": "喜茶(科技园店)",
      "billTime": "2023-10-25 14:30:00",
      "paymentMethod": "微信支付"
    }
  }
  ```

### 2. 文本描述解析（文本记账）
- **接口标识**：地址=`/ai/text-parse`，方法=POST
- **请求参数（JSON）**：
  | 参数名       | 类型   | 必传 | 说明                          | 示例值               |
  |--------------|--------|------|-------------------------------|----------------------|
  | text         | String | 是   | 用户输入的自然语言描述        | "今天下午2点半在肯德基花了45元吃炸鸡" |
- **响应示例**：
  ```json
  {
    "success": true,
    "code": 200,
    "message": "文本解析成功",
    "data": {
      "amount": 45.00,
      "type": 1,
      "categoryId": 1,
      "categoryName": "餐饮",
      "subCategoryId": 1,
      "subCategoryName": "快餐",
      "merchant": "肯德基",
      "billTime": "2023-10-25 14:30:00"
    }
  }
  ```


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

