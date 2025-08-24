package com.jasonlat.infrastructure.gateway;

import com.jasonlat.infrastructure.gateway.dto.CsdnPublishRequest;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * CSDN服务接口
 * 
 * <p>该接口使用Retrofit2框架定义了与CSDN平台交互的API调用方法。
 * 主要用于文章发布、编辑、删除等操作的网络请求封装。</p>
 * 
 * <p>接口特性：</p>
 * <ul>
 *   <li>基于Retrofit2框架，支持异步和同步调用</li>
 *   <li>使用注解方式定义HTTP请求参数</li>
 *   <li>支持JSON格式的请求体和响应体</li>
 *   <li>内置请求头管理，简化API调用</li>
 * </ul>
 * 
 * <p>认证方式：</p>
 * <ul>
 *   <li>使用Cookie进行用户身份认证</li>
 *   <li>需要有效的CSDN登录状态</li>
 *   <li>支持X-Ca-Key等安全头部验证</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * ICSDNService service = CsdnRetrofitConfig.createCsdnService();
 * CsdnPublishRequest request = CsdnPublishRequest.builder()
 *     .title("文章标题")
 *     .content("文章内容")
 *     .build();
 * Call<CsdnPublishResponse> call = service.publishArticle("your-cookie", request);
 * Response<CsdnPublishResponse> response = call.execute();
 * }</pre>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see CsdnPublishRequest
 * @see CsdnPublishResponse
 * @see com.jasonlat.infrastructure.gateway.CsdnRetrofitConfig
 */
public interface ICSDNService {

    /**
     * 发布文章到CSDN平台
     * 
     * <p>该方法用于将文章内容发布到CSDN博客平台。支持发布新文章和编辑已有文章。</p>
     * 
     * <p>请求特性：</p>
     * <ul>
     *   <li>使用POST方法向CSDN API发送请求</li>
     *   <li>请求体为JSON格式，包含文章的完整信息</li>
     *   <li>需要有效的用户Cookie进行身份认证</li>
     *   <li>包含必要的安全头部信息</li>
     * </ul>
     * 
     * <p>请求头说明：</p>
     * <ul>
     *   <li>Content-Type: 指定请求体为JSON格式</li>
     *   <li>User-Agent: 模拟浏览器请求，避免被反爬虫拦截</li>
     *   <li>X-Ca-Key: CSDN API密钥，用于请求验证</li>
     *   <li>X-Ca-Nonce: 随机数，防止重放攻击</li>
     *   <li>X-Ca-Signature: 请求签名，确保请求完整性</li>
     *   <li>Sec-Fetch-*: 浏览器安全策略相关头部</li>
     * </ul>
     * 
     * <p>使用注意事项：</p>
     * <ul>
     *   <li>Cookie必须是有效的CSDN登录状态</li>
     *   <li>文章内容需要符合CSDN平台规范</li>
     *   <li>建议先保存为草稿，确认无误后再发布</li>
     *   <li>频繁调用可能触发平台限流机制</li>
     * </ul>
     * 
     * @param cookie Cookie信息，用于身份验证，格式为"key1=value1; key2=value2"
     * @param request 文章发布请求对象，包含标题、内容、标签等完整信息
     * @return Call对象，可执行同步或异步请求获取发布结果
     * 
     * @throws IllegalArgumentException 当请求参数为null或无效时
     * @throws retrofit2.HttpException 当HTTP请求失败时
     * @throws java.io.IOException 当网络连接异常时
     * 
     * @see CsdnPublishRequest 请求参数详细说明
     * @see CsdnPublishResponse 响应结果详细说明
     * 
     * @since 1.0
     */
    @POST("blog-console-api/v3/mdeditor/saveArticle")
    @Headers({
        "Accept: */*",
        "Accept-Language: zh-CN,zh;q=0.9",
        "Content-Type: application/json",
        "DNT: 1",
        "Priority: u=1, i",
        "Sec-Ch-Ua: \"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"",
        "Sec-Ch-Ua-Mobile: ?0",
        "Sec-Ch-Ua-Platform: \"macOS\"",
        "Sec-Fetch-Dest: empty",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Site: same-site",
        "X-Ca-Key: 203803574",
        "X-Ca-Nonce: 138d7d05-2600-482d-83a6-62e6c02bdc17",
        "X-Ca-Signature: I1z2XgTgYqo839qPZYINgKRHWp3v7XlHO8QbmLDKMDA=",
        "X-Ca-Signature-Headers: x-ca-key,x-ca-nonce",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36",
        "Referer: https://editor.csdn.net/",
        "Origin: https://editor.csdn.net"
    })
    Call<CsdnPublishResponse> publishArticle(
        @Header("Cookie") String cookie,
        @Body CsdnPublishRequest request
    );
}