package com.jasonlat.infrastructure.gateway.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * CSDN文章发布响应DTO
 * 
 * <p>该类用于封装CSDN文章发布接口的响应数据，包含完整的响应信息。
 * 响应结构遵循CSDN API的标准格式，包含状态码、追踪ID、响应数据和消息等。</p>
 * 
 * <p>响应数据结构说明：</p>
 * <ul>
 *   <li>code: HTTP状态码，200表示成功</li>
 *   <li>traceId: 请求追踪ID，用于问题排查</li>
 *   <li>data: 核心响应数据，包含文章的详细信息</li>
 *   <li>msg: 响应消息，通常为"success"或错误描述</li>
 * </ul>
 * 
 * <p>data字段包含的文章信息：</p>
 * <ul>
 *   <li>url: 发布成功后的文章访问链接</li>
 *   <li>id: CSDN系统分配的文章唯一标识</li>
 *   <li>qrcode: 文章二维码图片URL，便于分享</li>
 *   <li>title: 文章标题</li>
 *   <li>description: 文章描述或摘要</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsdnPublishResponse {

    /**
     * 响应状态码
     * 
     * <p>HTTP状态码，用于标识请求处理结果：</p>
     * <ul>
     *   <li>200: 请求成功，文章发布成功</li>
     *   <li>400: 请求参数错误</li>
     *   <li>401: 认证失败，Cookie无效或过期</li>
     *   <li>403: 权限不足</li>
     *   <li>500: 服务器内部错误</li>
     * </ul>
     */
    private Integer code;

    /**
     * 请求追踪ID
     * 
     * <p>CSDN系统生成的唯一追踪标识，用于：</p>
     * <ul>
     *   <li>问题排查和日志追踪</li>
     *   <li>请求链路监控</li>
     *   <li>技术支持时的问题定位</li>
     * </ul>
     * 
     * <p>格式通常为UUID或时间戳组合的字符串</p>
     */
    @SerializedName("traceId")
    private String traceId;

    /**
     * 核心响应数据
     * 
     * <p>包含文章发布成功后的详细信息，如文章URL、ID、二维码等。
     * 当code为200时，该字段包含有效数据；当请求失败时，该字段可能为null。</p>
     */
    private CsdnPublishData data;

    /**
     * 响应消息
     * 
     * <p>描述请求处理结果的文本信息：</p>
     * <ul>
     *   <li>成功时通常为"success"</li>
     *   <li>失败时包含具体的错误描述</li>
     *   <li>可用于向用户展示操作结果</li>
     * </ul>
     */
    private String msg;

    /**
     * CSDN文章发布数据
     * 
     * <p>封装文章发布成功后返回的核心数据信息。
     * 该类包含了用户最关心的文章相关信息，如访问链接、唯一标识等。</p>
     * 
     * <p>所有字段在文章发布成功时都会有值，失败时整个对象可能为null。</p>
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CsdnPublishData {

        /**
         * 文章访问URL
         * 
         * <p>文章发布成功后的完整访问链接，格式通常为：</p>
         * <code>https://blog.csdn.net/{username}/article/details/{articleId}</code>
         * 
         * <p>用户可以通过此链接直接访问已发布的文章。</p>
         */
        private String url;

        /**
         * 文章唯一标识ID
         * 
         * <p>CSDN系统为文章分配的唯一数字标识，用于：</p>
         * <ul>
         *   <li>文章的唯一标识和检索</li>
         *   <li>后续的文章编辑、删除等操作</li>
         *   <li>构建文章访问URL</li>
         *   <li>数据统计和分析</li>
         * </ul>
         */
        private Long id;

        /**
         * 文章二维码图片URL
         * 
         * <p>CSDN生成的文章二维码图片链接，用于：</p>
         * <ul>
         *   <li>移动端快速访问文章</li>
         *   <li>社交媒体分享</li>
         *   <li>线下推广和展示</li>
         * </ul>
         * 
         * <p>二维码扫描后直接跳转到文章页面。</p>
         */
        private String qrcode;

        /**
         * 文章标题
         * 
         * <p>返回的文章标题，与发布请求中的标题一致。
         * 用于确认文章发布的标题信息是否正确。</p>
         */
        private String title;

        /**
         * 文章描述或摘要
         * 
         * <p>文章的简短描述或自动生成的摘要信息，用于：</p>
         * <ul>
         *   <li>搜索引擎优化(SEO)</li>
         *   <li>文章列表页面的预览</li>
         *   <li>社交媒体分享时的描述文本</li>
         * </ul>
         * 
         * <p>通常从文章内容的前几段自动提取生成。</p>
         */
        private String description;
    }
}