# CSDN文章发布MCP服务器

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)
[![MCP](https://img.shields.io/badge/MCP-Compatible-purple.svg)](https://modelcontextprotocol.io/)

一个基于Spring Boot和MCP（Model Context Protocol）协议的CSDN文章自动发布服务，支持AI助手通过标准化接口发布文章到CSDN博客平台。

## 🚀 功能特性

- **🤖 AI集成**: 完全兼容MCP协议，支持Claude、ChatGPT等AI助手直接调用
- **📝 Markdown支持**: 自动将Markdown内容转换为CSDN兼容的HTML格式
- **🏗️ DDD架构**: 采用领域驱动设计，代码结构清晰，易于维护和扩展
- **⚡ 高性能**: 基于Spring Boot 3.0+和Java 17+，性能优异
- **🔧 配置灵活**: 支持多种配置方式，适应不同部署环境
- **📊 日志完善**: 详细的操作日志，便于问题排查和监控
- **🛡️ 安全可靠**: 支持Cookie认证，确保账号安全

## 📋 系统要求

- **Java**: 17 或更高版本
- **Maven**: 3.6+ （用于构建）
- **CSDN账号**: 需要有效的CSDN登录Cookie
- **网络**: 能够访问CSDN API的网络环境

## 🛠️ 快速开始

### 1. 获取项目

```bash
git clone https://github.com/jssonlat-dot/csdn-publish-mcp-server.git
cd csdn-publish-mcp-server
```

### 2. 构建项目

```bash
mvn clean package -DskipTests
```

构建完成后，JAR文件将生成在 `target/csdn-publish-mcp-server-1.0.jar`

### 3. 获取CSDN Cookie

1. 登录 [CSDN官网](https://www.csdn.net/)
2. 打开浏览器开发者工具（F12）
3. 切换到 Network 标签页
4. 刷新页面或进行任意操作
5. 找到请求头中的 `Cookie` 字段，复制完整的Cookie值

### 4. 配置MCP服务器

在你的MCP客户端配置文件中添加以下配置：

```json
{
  "mcpServers": {
    "csdn-publish-mcp-server": {
      "command": "java",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-Dfile.encoding=utf-8",
        "-Dlogging.file.name=./data/mcp/${spring.application.name}.log",
        "-Dcsdn.api.cookie=你的CSDN_COOKIE",
        "-Dcsdn.api.categories=java面试宝典",
        "-jar",
        "path/to/csdn-publish-mcp-server-1.0.jar"
      ]
    }
  }
}
```

### 5. 配置参数说明

| 参数 | 说明 | 示例值 | 必填 |
|------|------|--------|------|
| `csdn.api.cookie` | CSDN登录Cookie | `uuid_tt_dd=...` | ✅ |
| `csdn.api.categories` | 文章分类 | `java面试宝典` | ✅ |
| `logging.file.name` | 日志文件路径 | `./data/mcp/csdn.log` | ❌ |
| `spring.ai.mcp.server.stdio` | MCP标准输入输出模式 | `true` | ✅ |
| `file.encoding` | 文件编码 | `utf-8` | ❌ |

## 📖 使用方法

### 通过AI助手使用

配置完成后，你可以直接向支持MCP的AI助手发送指令：

```
请帮我发布一篇文章到CSDN：
标题：Spring Boot集成Redis实现缓存优化
内容：[你的Markdown内容]
标签：Spring Boot,Redis,缓存,Java
描述：详细介绍Spring Boot集成Redis的配置方法和最佳实践
```

AI助手将自动调用MCP服务发布文章到CSDN。

### 直接API调用

如果需要直接调用API，可以使用以下格式：

```json
{
  "title": "文章标题",
  "markdowncontent": "# 文章内容\n\n这是Markdown格式的内容",
  "tags": "Java,Spring Boot,技术分享",
  "Description": "文章简要描述"
}
```

## 🏗️ 项目架构

本项目采用DDD（领域驱动设计）架构，主要包含以下层次：

```
├── domain/                 # 领域层
│   ├── model/             # 领域模型
│   ├── service/           # 领域服务
│   └── adapter/           # 端口接口
├── infrastructure/        # 基础设施层
│   ├── adapter/           # 适配器实现
│   └── config/            # 配置类
├── config/                # 应用配置
└── McpServerApplication   # 应用启动类
```

### 核心组件

- **ArticleFunctionRequest**: 文章发布请求领域模型
- **ArticleFunctionResponse**: 文章发布响应领域模型
- **ICsdnPort**: CSDN端口适配器接口
- **CsdnPort**: CSDN适配器实现
- **CsdnArticleService**: 文章发布领域服务

## 🔧 高级配置

### 自定义配置文件

创建 `application.yml` 文件：

```yaml
csdn:
  api:
    cookie: "你的CSDN_COOKIE"
    categories: "技术分享"

logging:
  level:
    com.jasonlat: DEBUG
  file:
    name: ./logs/csdn-mcp-server.log

spring:
  ai:
    mcp:
      server:
        stdio: true
```

### 环境变量配置

```bash
export CSDN_API_COOKIE="你的CSDN_COOKIE"
export CSDN_API_CATEGORIES="技术分享"
java -jar csdn-publish-mcp-server-1.0.jar
```

## 📝 开发指南

### 本地开发

1. 克隆项目并导入IDE
2. 配置环境变量或application.yml
3. 运行 `McpServerApplication.main()` 方法
4. 使用测试类 `ApiTest` 进行功能验证

### 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试
mvn test -Dtest=ApiTest
```

### 构建部署

```bash
# 构建JAR包
mvn clean package

# 构建Docker镜像（如果有Dockerfile）
docker build -t csdn-mcp-server .
```

## 🐛 故障排除

### 常见问题

**Q: Cookie无效或过期**
A: 重新登录CSDN获取新的Cookie，并更新配置

**Q: 文章发布失败**
A: 检查网络连接、Cookie有效性和文章内容格式

**Q: MCP连接失败**
A: 确认JAR包路径正确，Java版本符合要求

**Q: 中文乱码问题**
A: 确保启动参数包含 `-Dfile.encoding=utf-8`

### 日志查看

```bash
# 查看实时日志
tail -f ./data/mcp/csdn-publish-mcp-server.log

# 查看错误日志
grep "ERROR" ./data/mcp/csdn-publish-mcp-server.log
```

## 🤝 贡献指南

我们欢迎所有形式的贡献！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

### 开发规范

- 遵循Java编码规范
- 添加适当的单元测试
- 更新相关文档
- 确保所有测试通过

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- [Spring Boot](https://spring.io/projects/spring-boot) - 优秀的Java应用框架
- [Spring AI](https://spring.io/projects/spring-ai) - AI集成框架
- [MCP Protocol](https://modelcontextprotocol.io/) - 模型上下文协议
- [CSDN](https://www.csdn.net/) - 技术博客平台


⭐ 如果这个项目对你有帮助，请给它一个星标！