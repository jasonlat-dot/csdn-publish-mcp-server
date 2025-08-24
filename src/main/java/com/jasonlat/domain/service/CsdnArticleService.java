package com.jasonlat.domain.service;

import com.jasonlat.domain.adapter.ICsdnPort;
import com.jasonlat.domain.model.ArticleFunctionRequest;
import com.jasonlat.domain.model.ArticleFunctionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * CSDN文章发布领域服务类
 * 
 * <p>该类是基于DDD（领域驱动设计）架构的领域服务层实现，
 * 负责处理CSDN文章发布的核心业务逻辑。</p>
 * 
 * <p>领域服务特性：</p>
 * <ul>
 *   <li>位于领域层（Domain Layer），包含核心业务逻辑</li>
 *   <li>通过端口适配器模式与基础设施层解耦</li>
 *   <li>集成Spring AI工具注解，支持AI助手调用</li>
 *   <li>提供统一的文章发布服务接口</li>
 *   <li>负责业务流程编排和异常处理</li>
 * </ul>
 * 
 * <p>架构职责：</p>
 * <ul>
 *   <li>封装文章发布的业务逻辑</li>
 *   <li>协调领域对象之间的交互</li>
 *   <li>调用端口适配器执行具体操作</li>
 *   <li>处理业务异常和日志记录</li>
 *   <li>为AI工具提供标准化接口</li>
 * </ul>
 * 
 * <p>Spring AI集成：</p>
 * <ul>
 *   <li>使用@Tool注解标记可被AI调用的方法</li>
 *   <li>支持MCP协议与AI助手通信</li>
 *   <li>提供结构化的工具描述信息</li>
 *   <li>自动处理参数映射和结果转换</li>
 * </ul>
 * 
 * <p>依赖关系：</p>
 * <ul>
 *   <li>ICsdnPort - CSDN端口适配器接口</li>
 *   <li>ArticleFunctionRequest - 文章发布请求模型</li>
 *   <li>ArticleFunctionResponse - 文章发布响应模型</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>AI助手通过MCP协议调用文章发布功能</li>
 *   <li>其他应用服务需要发布CSDN文章</li>
 *   <li>批量文章发布和内容管理</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see ICsdnPort
 * @see ArticleFunctionRequest
 * @see ArticleFunctionResponse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CsdnArticleService {

    /**
     * CSDN端口适配器接口
     * 
     * <p>通过依赖注入获取的端口适配器实例，
     * 用于与基础设施层的CSDN API服务进行交互。</p>
     * 
     * <p>设计模式：</p>
     * <ul>
     *   <li>端口适配器模式 - 实现领域层与基础设施层解耦</li>
     *   <li>依赖倒置原则 - 领域层定义接口，基础设施层实现</li>
     *   <li>单一职责原则 - 专门负责CSDN相关操作</li>
     * </ul>
     */
    private final ICsdnPort csdnPort;

    /**
     * 发布文章到CSDN平台
     * 
     * <p>该方法是领域服务的核心业务方法，负责处理文章发布的完整流程。
     * 通过Spring AI的@Tool注解标记，可被AI助手直接调用。</p>
     * 
     * <p>业务流程：</p>
     * <ol>
     *   <li>接收文章发布请求参数</li>
     *   <li>记录业务操作日志</li>
     *   <li>调用端口适配器执行发布操作</li>
     *   <li>返回发布结果响应</li>
     * </ol>
     * 
     * <p>Spring AI工具配置：</p>
     * <ul>
     *   <li>工具名称：saveCsdnArticle</li>
     *   <li>工具描述：发布Csdn文章</li>
     *   <li>支持AI助手通过MCP协议调用</li>
     *   <li>自动处理参数验证和类型转换</li>
     * </ul>
     * 
     * <p>请求参数说明：</p>
     * <ul>
     *   <li>title - 文章标题（必填）</li>
     *   <li>markdowncontent - Markdown格式内容（必填）</li>
     *   <li>content - HTML格式内容（必填）</li>
     *   <li>tags - 文章标签（必填）</li>
     *   <li>description - 文章描述（必填）</li>
     * </ul>
     * 
     * <p>响应结果说明：</p>
     * <ul>
     *   <li>code - 响应状态码</li>
     *   <li>msg - 响应消息</li>
     *   <li>id - 文章ID（成功时返回）</li>
     *   <li>url - 文章URL（成功时返回）</li>
     * </ul>
     * 
     * <p>异常处理：</p>
     * <ul>
     *   <li>IOException - 网络请求异常或API调用失败</li>
     *   <li>业务异常会记录详细日志便于排查</li>
     *   <li>异常信息会传播到上层调用者</li>
     * </ul>
     * 
     * @param request 文章发布请求对象，包含文章的所有必要信息
     * @return ArticleFunctionResponse 发布结果响应，包含成功/失败状态和相关信息
     * @throws IOException 当网络请求发生异常或API调用失败时抛出
     * 
     * @see Tool
     * @see ArticleFunctionRequest
     * @see ArticleFunctionResponse
     * @see ICsdnPort#writeArticle(ArticleFunctionRequest)
     */
    @Tool(name = "saveCsdnArticle", description = "发布Csdn文章")
    public ArticleFunctionResponse saveCsdnArticle(ArticleFunctionRequest request) throws IOException {
        log.info("开始发布CSDN文章，标题: {} 标签: {}", request.getTitle(), request.getTags());
        return csdnPort.writeArticle(request);
    }
}
