package com.jasonlat.domain.adapter;

import com.jasonlat.domain.model.ArticleFunctionRequest;
import com.jasonlat.domain.model.ArticleFunctionResponse;

import java.io.IOException;

/**
 * CSDN端口适配器接口
 * 
 * <p>该接口是基于DDD（领域驱动设计）架构的端口接口，
 * 定义了与CSDN平台交互的标准契约。</p>
 * 
 * <p>架构定位：</p>
 * <ul>
 *   <li>位于领域层（Domain Layer），定义业务能力</li>
 *   <li>遵循端口-适配器模式（Hexagonal Architecture）</li>
 *   <li>实现依赖倒置原则，领域层不依赖基础设施层</li>
 *   <li>提供清晰的业务边界和抽象</li>
 * </ul>
 * 
 * <p>设计特性：</p>
 * <ul>
 *   <li>@FunctionalInterface - 函数式接口，支持Lambda表达式</li>
 *   <li>单一职责原则 - 专注于文章发布功能</li>
 *   <li>接口隔离原则 - 最小化接口依赖</li>
 *   <li>开闭原则 - 对扩展开放，对修改封闭</li>
 * </ul>
 * 
 * <p>实现要求：</p>
 * <ul>
 *   <li>实现类应位于基础设施层（Infrastructure Layer）</li>
 *   <li>负责具体的CSDN API调用和数据转换</li>
 *   <li>处理网络异常和业务异常</li>
 *   <li>确保数据格式的正确转换</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>领域服务通过此接口发布文章到CSDN</li>
 *   <li>支持依赖注入和测试替换</li>
 *   <li>实现业务逻辑与技术实现的解耦</li>
 *   <li>便于单元测试和集成测试</li>
 * </ul>
 * 
 * <p>异常处理：</p>
 * <ul>
 *   <li>IOException - 网络连接异常、API调用失败</li>
 *   <li>业务异常 - 通过返回值的状态码表示</li>
 *   <li>参数验证 - 实现类负责参数校验</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2024-01-01
 * @see ArticleFunctionRequest
 * @see ArticleFunctionResponse
 * @see com.jasonlat.infrastructure.adapter.CsdnPort
 */
@FunctionalInterface
public interface ICsdnPort {

    /**
     * 发布文章到CSDN平台
     * 
     * <p>核心业务方法，负责将文章内容发布到CSDN博客平台，
     * 并返回发布结果的详细信息。</p>
     * 
     * <p>处理流程：</p>
     * <ol>
     *   <li>接收领域模型格式的文章发布请求</li>
     *   <li>验证请求参数的完整性和有效性</li>
     *   <li>转换为CSDN API所需的数据格式</li>
     *   <li>调用CSDN发布接口进行文章创建</li>
     *   <li>解析API响应并转换为领域模型</li>
     *   <li>返回标准化的发布结果</li>
     * </ol>
     * 
     * <p>数据转换：</p>
     * <ul>
     *   <li>ArticleFunctionRequest → CsdnPublishRequest</li>
     *   <li>Markdown内容 → HTML格式内容</li>
     *   <li>标签字符串 → 标签数组</li>
     *   <li>CsdnPublishResponse → ArticleFunctionResponse</li>
     * </ul>
     * 
     * <p>成功条件：</p>
     * <ul>
     *   <li>所有必填字段均已提供且格式正确</li>
     *   <li>CSDN Cookie有效且具有发布权限</li>
     *   <li>网络连接正常，API服务可用</li>
     *   <li>文章内容符合CSDN平台规范</li>
     * </ul>
     * 
     * <p>失败场景：</p>
     * <ul>
     *   <li>参数验证失败 - 返回400状态码</li>
     *   <li>认证失败 - 返回401状态码</li>
     *   <li>权限不足 - 返回403状态码</li>
     *   <li>网络异常 - 抛出IOException</li>
     *   <li>API异常 - 返回500状态码</li>
     * </ul>
     * 
     * @param request 文章发布请求，包含标题、内容、标签等信息
     * @return 文章发布响应，包含状态码、消息、URL和文章ID
     * @throws IOException 当网络连接失败、API调用异常或数据传输错误时抛出
     * 
     * @see ArticleFunctionRequest 文章发布请求模型
     * @see ArticleFunctionResponse 文章发布响应模型
     * @see com.jasonlat.infrastructure.adapter.CsdnPort#writeArticle(ArticleFunctionRequest)
     */
    ArticleFunctionResponse writeArticle(ArticleFunctionRequest request) throws IOException;
}
