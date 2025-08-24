package com.jasonlat.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishResponse;
import lombok.Data;

/**
 * CSDN文章发布响应领域模型
 * 
 * <p>该类是基于DDD（领域驱动设计）架构的领域模型，
 * 用于封装文章发布操作的响应结果信息。</p>
 * 
 * <p>模型特性：</p>
 * <ul>
 *   <li>位于领域层（Domain Layer），表示业务操作结果</li>
 *   <li>使用Jackson注解支持JSON序列化/反序列化</li>
 *   <li>集成Spring AI工具响应映射</li>
 *   <li>提供标准化的API响应格式</li>
 *   <li>支持链式调用和函数式编程</li>
 * </ul>
 * 
 * <p>响应结构说明：</p>
 * <ul>
 *   <li>code - HTTP状态码或业务状态码</li>
 *   <li>msg - 响应消息，包含成功提示或错误描述</li>
 *   <li>url - 发布成功后的文章访问链接</li>
 *   <li>id - CSDN平台分配的文章唯一标识</li>
 * </ul>
 * 
 * <p>JSON配置说明：</p>
 * <ul>
 *   <li>@JsonInclude(NON_NULL) - 忽略null值字段</li>
 *   <li>@JsonProperty - 定义JSON字段名和必填属性</li>
 *   <li>@JsonPropertyDescription - 提供字段描述信息</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>AI助手接收文章发布操作的结果反馈</li>
 *   <li>领域服务返回业务操作执行状态</li>
 *   <li>端口适配器进行响应数据转换</li>
 *   <li>MCP协议传递操作结果给客户端</li>
 * </ul>
 * 
 * <p>状态码约定：</p>
 * <ul>
 *   <li>200 - 发布成功</li>
 *   <li>400 - 请求参数错误</li>
 *   <li>401 - 认证失败（Cookie无效）</li>
 *   <li>403 - 权限不足</li>
 *   <li>500 - 服务器内部错误</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see ArticleFunctionRequest
 * @see CsdnPublishResponse
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFunctionResponse {

    /**
     * 响应状态码
     * 
     * <p>表示文章发布操作的执行状态，
     * 遵循HTTP状态码规范和业务状态约定。</p>
     * 
     * <p>常见状态码：</p>
     * <ul>
     *   <li>200 - 发布成功，文章已成功创建</li>
     *   <li>400 - 请求参数错误，如标题为空、内容格式不正确</li>
     *   <li>401 - 认证失败，Cookie已过期或无效</li>
     *   <li>403 - 权限不足，账号被限制发布</li>
     *   <li>429 - 请求过于频繁，触发限流机制</li>
     *   <li>500 - 服务器内部错误，CSDN API异常</li>
     * </ul>
     * 
     * <p>业务处理：</p>
     * <ul>
     *   <li>成功状态（2xx）：可以获取文章URL和ID</li>
     *   <li>客户端错误（4xx）：检查请求参数和认证信息</li>
     *   <li>服务器错误（5xx）：建议重试或联系技术支持</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "code")
    @JsonPropertyDescription("响应状态码")
    private Integer code;

    /**
     * 响应消息
     * 
     * <p>提供操作结果的详细描述信息，
     * 包含成功提示或具体的错误原因。</p>
     * 
     * <p>消息类型：</p>
     * <ul>
     *   <li>成功消息："文章发布成功"、"Article published successfully"</li>
     *   <li>参数错误："标题不能为空"、"文章内容过短"</li>
     *   <li>认证错误："Cookie已过期，请重新登录"</li>
     *   <li>权限错误："账号发布权限受限"</li>
     *   <li>系统错误："CSDN服务暂时不可用"</li>
     * </ul>
     * 
     * <p>消息特点：</p>
     * <ul>
     *   <li>必填字段，提供明确的操作反馈</li>
     *   <li>支持中英文双语，便于国际化</li>
     *   <li>错误消息应具体指出问题所在</li>
     *   <li>成功消息应简洁明了</li>
     * </ul>
     * 
     * <p>使用建议：</p>
     * <ul>
     *   <li>AI助手可直接展示给用户</li>
     *   <li>开发者可用于日志记录和调试</li>
     *   <li>客户端可根据消息内容进行相应处理</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "msg")
    @JsonPropertyDescription("响应消息")
    private String msg;


    /**
     * 文章访问URL
     *
     * <p>文章发布成功后，CSDN平台分配的文章访问链接，
     * 用户可通过此链接直接访问已发布的文章。</p>
     *
     * <p>URL特性：</p>
     * <ul>
     *   <li>仅在发布成功时返回（code=200）</li>
     *   <li>格式：<a href="https://blog.csdn.net/">...</a>{username}/article/details/{id}</li>
     *   <li>永久有效，除非文章被删除</li>
     *   <li>支持SEO优化，便于搜索引擎收录</li>
     * </ul>
     *
     * <p>使用场景：</p>
     * <ul>
     *   <li>AI助手向用户展示发布结果</li>
     *   <li>自动化工具进行文章链接收集</li>
     *   <li>社交媒体分享和推广</li>
     *   <li>内容管理系统的链接存储</li>
     * </ul>
     *
     * <p>示例URL：</p>
     * <ul>
     *   <li>https://blog.csdn.net/jasonlat/article/details/123456789</li>
     *   <li>https://blog.csdn.net/developer/article/details/987654321</li>
     * </ul>
     *
     * <p>注意事项：</p>
     * <ul>
     *   <li>发布失败时此字段可能为null</li>
     *   <li>URL中包含用户名和文章ID信息</li>
     *   <li>可用于验证文章是否成功发布</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "url")
    @JsonPropertyDescription("文章访问URL")
    private String url;


    /**
     * 文章唯一标识ID
     * 
     * <p>CSDN平台为每篇文章分配的唯一数字标识符，
     * 用于在系统内部唯一标识和管理文章。</p>
     * 
     * <p>ID特性：</p>
     * <ul>
     *   <li>仅在发布成功时返回（code=200）</li>
     *   <li>数据类型：Long，支持大数值</li>
     *   <li>全局唯一，不会重复</li>
     *   <li>递增生成，新文章ID更大</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>文章管理：编辑、删除、查询操作</li>
     *   <li>数据统计：文章阅读量、评论数统计</li>
     *   <li>关联操作：评论、点赞、收藏等</li>
     *   <li>API调用：作为其他CSDN API的参数</li>
     * </ul>
     * 
     * <p>ID示例：</p>
     * <ul>
     *   <li>123456789L - 较早发布的文章</li>
     *   <li>987654321L - 较新发布的文章</li>
     * </ul>
     * 
     * <p>技术说明：</p>
     * <ul>
     *   <li>使用Long类型避免整数溢出</li>
     *   <li>可用于构建文章URL</li>
     *   <li>便于数据库索引和查询优化</li>
     *   <li>支持分布式系统的ID生成策略</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>发布失败时此字段可能为null</li>
     *   <li>ID一旦分配不会改变</li>
     *   <li>可用于验证文章发布状态</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "id")
    @JsonPropertyDescription("文章唯一标识ID")
    private Long id;

}
