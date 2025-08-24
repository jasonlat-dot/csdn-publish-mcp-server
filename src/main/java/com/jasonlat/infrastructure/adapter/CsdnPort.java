package com.jasonlat.infrastructure.adapter;

import com.jasonlat.config.CsdnApiProperties;
import com.jasonlat.domain.adapter.ICsdnPort;
import com.jasonlat.domain.model.ArticleFunctionRequest;
import com.jasonlat.domain.model.ArticleFunctionResponse;
import com.jasonlat.infrastructure.gateway.ICSDNService;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishRequest;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * CSDN端口适配器实现类
 * 
 * <p>该类是基于DDD（领域驱动设计）架构的端口适配器模式实现，
 * 负责将领域层的接口适配到基础设施层的具体实现。</p>
 * 
 * <p>主要职责：</p>
 * <ul>
 *   <li>实现领域层定义的ICsdnPort接口</li>
 *   <li>将领域对象转换为基础设施层的DTO对象</li>
 *   <li>调用CSDN API服务进行文章发布</li>
 *   <li>处理API响应并转换为领域响应对象</li>
 *   <li>统一异常处理和日志记录</li>
 * </ul>
 * 
 * <p>架构层次：</p>
 * <ul>
 *   <li>位于基础设施层（Infrastructure Layer）</li>
 *   <li>实现领域层接口，遵循依赖倒置原则</li>
 *   <li>依赖注入方式获取外部服务和配置</li>
 * </ul>
 * 
 * <p>数据转换流程：</p>
 * <ol>
 *   <li>接收领域层的ArticleFunctionRequest请求</li>
 *   <li>转换为基础设施层的CsdnPublishRequest</li>
 *   <li>调用ICSDNService进行API请求</li>
 *   <li>处理CsdnPublishResponse响应</li>
 *   <li>转换为领域层的ArticleFunctionResponse返回</li>
 * </ol>
 * 
 * <p>配置依赖：</p>
 * <ul>
 *   <li>ICSDNService - CSDN API服务接口</li>
 *   <li>CsdnApiProperties - CSDN API配置属性</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see ICsdnPort
 * @see ICSDNService
 * @see CsdnApiProperties
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CsdnPort implements ICsdnPort {

    /**
     * CSDN API服务接口
     * 
     * <p>通过依赖注入获取的Retrofit服务实例，
     * 用于执行具体的CSDN API调用操作。</p>
     */
    private final ICSDNService icsdnService;
    
    /**
     * CSDN API配置属性
     * 
     * <p>包含CSDN API调用所需的配置信息，
     * 如Cookie认证信息、默认分类等。</p>
     */
    private final CsdnApiProperties csdnApiProperties;


    /**
     * 发布文章到CSDN平台
     * 
     * <p>该方法实现了领域层定义的文章发布接口，负责将领域请求转换为
     * 基础设施层的API调用，并处理响应结果。</p>
     * 
     * <p>处理流程：</p>
     * <ol>
     *   <li>将ArticleFunctionRequest转换为CsdnPublishRequest</li>
     *   <li>从配置中获取Cookie和分类信息</li>
     *   <li>调用CSDN API执行文章发布</li>
     *   <li>处理API响应，记录详细日志</li>
     *   <li>转换响应为ArticleFunctionResponse返回</li>
     * </ol>
     * 
     * <p>数据转换说明：</p>
     * <ul>
     *   <li>title - 文章标题，直接映射</li>
     *   <li>markdownContent - Markdown内容，直接映射</li>
     *   <li>content - HTML内容，直接映射</li>
     *   <li>tags - 文章标签，直接映射</li>
     *   <li>description - 文章描述，直接映射</li>
     *   <li>categories - 从配置文件中获取默认分类</li>
     * </ul>
     * 
     * <p>响应处理：</p>
     * <ul>
     *   <li>成功时：提取文章ID、URL等信息</li>
     *   <li>失败时：记录错误信息和状态码</li>
     *   <li>异常时：抛出IOException供上层处理</li>
     * </ul>
     * 
     * @param request 领域层文章发布请求对象，包含文章的基本信息
     * @return ArticleFunctionResponse 发布结果响应对象，包含成功/失败状态和相关信息
     * @throws IOException 当网络请求发生异常或API调用失败时抛出
     * 
     * @see ArticleFunctionRequest
     * @see ArticleFunctionResponse
     * @see CsdnPublishRequest
     * @see CsdnPublishResponse
     */
    @Override
    public ArticleFunctionResponse writeArticle(ArticleFunctionRequest request) throws IOException {

        // 构建CSDN API请求对象，将领域对象转换为基础设施层DTO
        CsdnPublishRequest articleRequest = CsdnPublishRequest.builder()
                .title(request.getTitle())
                .markdownContent(request.getMarkdowncontent())
                .content(request.getContent())
                .tags(request.getTags())
                .description(request.getDescription())
                .categories(csdnApiProperties.getCategories()) // 从配置中获取默认分类
                .build();

        log.info("开始发布CSDN文章，标题: {}", request.getTitle());

        // 调用CSDN API执行文章发布，使用配置中的Cookie进行认证
        Call<CsdnPublishResponse> call = icsdnService.publishArticle(csdnApiProperties.getCookie(), articleRequest);
        Response<CsdnPublishResponse> response = call.execute();

        log.info("API响应状态码: {}", response.code());
        log.info("API响应头: {}", response.headers());

        // 创建领域层响应对象
        ArticleFunctionResponse articleFunctionResponse = new ArticleFunctionResponse();

        // 处理API响应结果
        if (response.isSuccessful() && response.body() != null) {
            // 发布成功的处理逻辑
            CsdnPublishResponse result = response.body();
            log.info("文章发布成功！响应数据: {}", result);
            
            if (result.getData() != null) {
                log.info("文章ID: {}", result.getData().getId());
                log.info("文章URL: {}", result.getData().getUrl());
                
                // 将API响应转换为领域响应对象
                articleFunctionResponse.setCode(result.getCode());
                articleFunctionResponse.setMsg(result.getMsg());
                articleFunctionResponse.setId(result.getData().getId());
                articleFunctionResponse.setUrl(result.getData().getUrl());
            } else {
                log.warn("API响应成功但数据为空");
                articleFunctionResponse.setCode(result.getCode());
                articleFunctionResponse.setMsg(result.getMsg());
            }
        } else {
            // 发布失败的处理逻辑
            log.error("文章发布失败！HTTP状态码: {}", response.code());
            
            if (response.errorBody() != null) {
                String errorMsg = response.errorBody().string();
                log.error("API错误信息: {}", errorMsg);
                articleFunctionResponse.setMsg(errorMsg);
            } else {
                log.error("HTTP错误信息: {}", response.message());
                articleFunctionResponse.setMsg(response.message());
            }
            articleFunctionResponse.setCode(response.code());
        }

        return articleFunctionResponse;
    }
}
