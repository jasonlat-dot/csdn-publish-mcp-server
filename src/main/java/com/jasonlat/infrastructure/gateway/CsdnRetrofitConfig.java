package com.jasonlat.infrastructure.gateway;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * CSDN Retrofit配置类
 * 
 * <p>该类负责配置和创建与CSDN API交互所需的Retrofit实例和HTTP客户端。
 * 提供了统一的网络请求配置，包括超时设置、日志记录、JSON转换等功能。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>创建配置完整的Retrofit实例</li>
 *   <li>配置OkHttpClient，包含超时和日志设置</li>
 *   <li>提供ICSDNService接口的实例化方法</li>
 *   <li>统一管理CSDN API的基础配置</li>
 * </ul>
 * 
 * <p>配置特性：</p>
 * <ul>
 *   <li>连接超时：30秒</li>
 *   <li>读取超时：30秒</li>
 *   <li>写入超时：30秒</li>
 *   <li>支持HTTP请求/响应日志记录</li>
 *   <li>使用Gson进行JSON序列化/反序列化</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * // 创建CSDN服务实例
 * ICSDNService service = CsdnRetrofitConfig.createCsdnService();
 * 
 * // 或者获取Retrofit实例进行自定义配置
 * Retrofit retrofit = CsdnRetrofitConfig.createRetrofit();
 * ICSDNService service = retrofit.create(ICSDNService.class);
 * }</pre>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see ICSDNService
 * @see retrofit2.Retrofit
 * @see okhttp3.OkHttpClient
 */
public class CsdnRetrofitConfig {

    /**
     * CSDN API基础URL
     * 
     * <p>CSDN官方API的根地址，所有API请求都基于此URL构建。
     * 该地址指向CSDN的业务API服务器，用于文章发布、编辑等操作。</p>
     * 
     * <p>URL说明：</p>
     * <ul>
     *   <li>协议：HTTPS，确保数据传输安全</li>
     *   <li>域名：bizapi.csdn.net，CSDN业务API专用域名</li>
     *   <li>路径：根路径，具体API端点在接口方法中定义</li>
     * </ul>
     */
    private static final String BASE_URL = "https://bizapi.csdn.net/";

    /**
     * 连接超时时间（秒）
     * 
     * <p>建立TCP连接的最大等待时间。如果在指定时间内无法建立连接，
     * 将抛出ConnectTimeoutException异常。</p>
     * 
     * <p>设置为30秒的原因：</p>
     * <ul>
     *   <li>考虑到网络环境的复杂性</li>
     *   <li>CSDN服务器可能存在负载较高的情况</li>
     *   <li>为用户提供合理的等待时间</li>
     * </ul>
     */
    private static final int CONNECT_TIMEOUT = 300;

    /**
     * 读取超时时间（秒）
     * 
     * <p>从服务器读取响应数据的最大等待时间。如果在指定时间内无法完成数据读取，
     * 将抛出SocketTimeoutException异常。</p>
     * 
     * <p>设置为30秒的考虑：</p>
     * <ul>
     *   <li>文章发布可能涉及较大的数据传输</li>
     *   <li>服务器处理复杂请求需要一定时间</li>
     *   <li>避免因网络波动导致的请求失败</li>
     * </ul>
     */
    private static final int READ_TIMEOUT = 300;

    /**
     * 写入超时时间（秒）
     * 
     * <p>向服务器写入请求数据的最大等待时间。如果在指定时间内无法完成数据写入，
     * 将抛出SocketTimeoutException异常。</p>
     * 
     * <p>设置为30秒的原因：</p>
     * <ul>
     *   <li>文章内容可能较长，需要更多时间上传</li>
     *   <li>图片等媒体文件的上传需要额外时间</li>
     *   <li>确保大型请求能够成功发送</li>
     * </ul>
     */
    private static final int WRITE_TIMEOUT = 300;

    /**
     * 创建CSDN服务实例
     * 
     * <p>这是一个便捷方法，用于快速创建配置完整的ICSDNService实例。
     * 该方法内部调用createRetrofit()获取Retrofit实例，然后创建服务接口的实现。</p>
     * 
     * <p>创建的服务实例特性：</p>
     * <ul>
     *   <li>已配置完整的HTTP客户端（超时、日志等）</li>
     *   <li>已设置CSDN API的基础URL</li>
     *   <li>已配置Gson转换器用于JSON处理</li>
     *   <li>可直接用于API调用，无需额外配置</li>
     * </ul>
     * 
     * <p>使用示例：</p>
     * <pre>{@code
     * ICSDNService service = CsdnRetrofitConfig.createCsdnService();
     * CsdnPublishRequest request = CsdnPublishRequest.builder()
     *     .title("文章标题")
     *     .content("文章内容")
     *     .build();
     * Call<CsdnPublishResponse> call = service.publishArticle(request, "cookie");
     * }</pre>
     * 
     * @return 配置完整的ICSDNService实例，可直接用于API调用
     * 
     * @see #createRetrofit()
     * @see ICSDNService
     * 
     * @since 1.0
     */
    public static ICSDNService createCsdnService() {
        return createRetrofit().create(ICSDNService.class);
    }

    /**
     * 创建Retrofit实例
     * 
     * <p>创建并配置Retrofit实例，用于构建HTTP API接口。
     * 该方法配置了完整的网络请求处理链，包括基础URL、HTTP客户端和数据转换器。</p>
     * 
     * <p>配置组件说明：</p>
     * <ul>
     *   <li>baseUrl: 设置CSDN API的基础URL</li>
     *   <li>client: 使用自定义配置的OkHttpClient</li>
     *   <li>converterFactory: 使用Gson进行JSON序列化/反序列化</li>
     * </ul>
     * 
     * <p>Retrofit特性：</p>
     * <ul>
     *   <li>类型安全的HTTP客户端</li>
     *   <li>支持同步和异步调用</li>
     *   <li>自动处理JSON转换</li>
     *   <li>支持注解驱动的API定义</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>需要自定义配置时可直接使用此方法</li>
     *   <li>创建多个不同配置的服务实例</li>
     *   <li>集成到Spring等框架中作为Bean</li>
     * </ul>
     * 
     * @return 配置完整的Retrofit实例，包含HTTP客户端和JSON转换器
     * 
     * @see #createOkHttpClient()
     * @see retrofit2.converter.gson.GsonConverterFactory
     * 
     * @since 1.0
     */
    public static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 创建OkHttpClient实例
     * 
     * <p>创建并配置OkHttpClient，作为Retrofit的底层HTTP客户端。
     * 该客户端负责实际的网络请求执行，包括连接管理、超时控制、日志记录等。</p>
     * 
     * <p>配置的拦截器：</p>
     * <ul>
     *   <li>HttpLoggingInterceptor: 记录HTTP请求和响应的详细信息</li>
     *   <li>日志级别设置为BODY: 记录完整的请求体和响应体</li>
     * </ul>
     * 
     * <p>超时配置：</p>
     * <ul>
     *   <li>连接超时: {@value #CONNECT_TIMEOUT}秒</li>
     *   <li>读取超时: {@value #READ_TIMEOUT}秒</li>
     *   <li>写入超时: {@value #WRITE_TIMEOUT}秒</li>
     * </ul>
     * 
     * <p>日志记录说明：</p>
     * <ul>
     *   <li>开发环境：便于调试和问题排查</li>
     *   <li>生产环境：建议调整日志级别或移除敏感信息</li>
     *   <li>记录内容：请求URL、头部、参数、响应状态等</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>日志可能包含敏感信息（如Cookie），生产环境需谨慎</li>
     *   <li>超时时间可根据实际网络环境调整</li>
     *   <li>可根据需要添加其他拦截器（如重试、缓存等）</li>
     * </ul>
     * 
     * @return 配置完整的OkHttpClient实例，包含超时设置和日志拦截器
     * 
     * @see HttpLoggingInterceptor
     * @see okhttp3.OkHttpClient.Builder
     * 
     * @since 1.0
     */
    private static OkHttpClient createOkHttpClient() {
        // 创建日志拦截器，用于记录HTTP请求和响应的详细信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 设置日志级别为BODY，记录完整的请求体和响应体（包含敏感信息，生产环境需注意）
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                // 设置连接超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                // 设置读取超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 设置写入超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 添加日志拦截器
                .addInterceptor(loggingInterceptor)
                .build();
    }
}