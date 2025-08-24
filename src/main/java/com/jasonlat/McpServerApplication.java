
package com.jasonlat;

import com.jasonlat.config.CsdnApiProperties;
import com.jasonlat.domain.service.CsdnArticleService;
import com.jasonlat.infrastructure.gateway.CsdnRetrofitConfig;
import com.jasonlat.infrastructure.gateway.ICSDNService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * MCP服务器主应用程序启动类
 * 
 * <p>该类是基于Spring Boot框架的MCP（Model Context Protocol）服务器应用程序的入口点，
 * 集成了CSDN文章发布功能，为AI助手提供文章发布能力。</p>
 * 
 * <p>应用程序特性：</p>
 * <ul>
 *   <li>基于Spring Boot 3.x框架构建</li>
 *   <li>实现CommandLineRunner接口，支持启动后执行逻辑</li>
 *   <li>集成Spring AI工具回调机制</li>
 *   <li>提供CSDN API服务的Bean配置</li>
 *   <li>支持MCP协议与AI助手通信</li>
 * </ul>
 * 
 * <p>架构设计：</p>
 * <ul>
 *   <li>采用DDD（领域驱动设计）分层架构</li>
 *   <li>基于端口适配器模式实现外部系统集成</li>
 *   <li>使用依赖注入管理组件生命周期</li>
 *   <li>通过配置类统一管理外部服务配置</li>
 * </ul>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>启动MCP服务器，监听AI助手请求</li>
 *   <li>提供CSDN文章发布工具服务</li>
 *   <li>管理Retrofit HTTP客户端配置</li>
 *   <li>处理应用程序生命周期事件</li>
 * </ul>
 * 
 * <p>配置说明：</p>
 * <ul>
 *   <li>@SpringBootApplication - 启用Spring Boot自动配置</li>
 *   <li>@Configuration - 标识为配置类，可定义Bean</li>
 *   <li>@Slf4j - 启用Lombok日志功能</li>
 *   <li>CommandLineRunner - 应用启动后执行run方法</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * // 启动应用程序
 * java -jar csdn-publish-mcp-server.jar
 * 
 * // 或通过IDE运行main方法
 * McpServerApplication.main(new String[]{});
 * }</pre>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see CommandLineRunner
 * @see SpringBootApplication
 * @see ICSDNService
 * @see CsdnRetrofitConfig
 */
@Slf4j
@SpringBootApplication
public class McpServerApplication implements CommandLineRunner {

    @Resource
    private CsdnApiProperties csdnApiProperties;

    /**
     * 应用程序主入口方法
     * 
     * <p>启动Spring Boot应用程序，初始化Spring容器，
     * 加载所有配置的Bean，并启动MCP服务器。</p>
     * 
     * <p>启动流程：</p>
     * <ol>
     *   <li>初始化Spring应用上下文</li>
     *   <li>加载自动配置类和组件扫描</li>
     *   <li>创建并注册所有Bean实例</li>
     *   <li>执行CommandLineRunner.run()方法</li>
     *   <li>启动内嵌服务器（如果配置）</li>
     * </ol>
     * 
     * @param args 命令行参数，可用于传递配置参数
     */
    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    /**
     * 创建Spring AI工具回调提供者（已注释）
     * 
     * <p>该方法用于配置Spring AI的工具回调机制，
     * 允许AI助手调用应用程序中定义的工具方法。</p>
     * 
     * <p>配置说明：</p>
     * <ul>
     *   <li>MethodToolCallbackProvider - 基于方法的工具回调提供者</li>
     *   <li>toolObjects - 包含工具方法的对象数组</li>
     *   <li>支持通过注解标记工具方法</li>
     * </ul>
     * 
     * <p>注意：当前已注释，如需启用AI工具回调功能，
     * 请取消注释并配置相应的工具对象。</p>
     * 
     * @return ToolCallbackProvider 工具回调提供者实例
     */
    @Bean
    public ToolCallbackProvider csdnTools(CsdnArticleService csdnArticleService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(csdnArticleService) // 支持数组传参
                .build();
    }

    /**
     * 配置CSDN API服务Bean
     * 
     * <p>创建并配置CSDN API服务的Spring Bean实例，
     * 使用Retrofit2框架构建HTTP客户端。</p>
     * 
     * <p>配置特性：</p>
     * <ul>
     *   <li>基于Retrofit2框架的声明式HTTP客户端</li>
     *   <li>集成OkHttp3作为底层HTTP实现</li>
     *   <li>支持JSON序列化/反序列化</li>
     *   <li>配置连接超时、读取超时等参数</li>
     *   <li>支持Cookie认证和请求头配置</li>
     * </ul>
     * 
     * <p>Bean生命周期：</p>
     * <ul>
     *   <li>单例模式，应用启动时创建</li>
     *   <li>由Spring容器管理生命周期</li>
     *   <li>可通过依赖注入在其他组件中使用</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>CsdnPort适配器中调用CSDN API</li>
     *   <li>测试类中验证API功能</li>
     *   <li>其他需要访问CSDN服务的组件</li>
     * </ul>
     * 
     * @return ICSDNService 配置完成的CSDN API服务实例
     * @see ICSDNService
     * @see CsdnRetrofitConfig
     */
    @Bean
    public ICSDNService csdnService() {
        return CsdnRetrofitConfig.createCsdnService();
    }

    /**
     * 应用程序启动完成后的回调方法
     * 
     * <p>实现CommandLineRunner接口的run方法，
     * 在Spring Boot应用程序完全启动后执行。</p>
     * 
     * <p>执行时机：</p>
     * <ul>
     *   <li>所有Bean初始化完成后</li>
     *   <li>自动配置全部加载完成后</li>
     *   <li>应用上下文完全刷新后</li>
     *   <li>在应用程序开始接受请求前</li>
     * </ul>
     * 
     * <p>主要用途：</p>
     * <ul>
     *   <li>记录应用程序启动成功日志</li>
     *   <li>执行初始化数据加载（如需要）</li>
     *   <li>验证关键组件是否正常工作</li>
     *   <li>输出应用程序状态信息</li>
     * </ul>
     * 
     * <p>扩展建议：</p>
     * <ul>
     *   <li>可添加健康检查逻辑</li>
     *   <li>可验证外部服务连接状态</li>
     *   <li>可输出应用程序配置信息</li>
     *   <li>可执行预热操作</li>
     * </ul>
     * 
     * @param args 命令行参数，与main方法参数相同
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("check csdn cookie ...");
        if (csdnApiProperties.getCookie() == null) {
            log.warn("csdn cookie key is null, please set it in application.yml");
        } else {
            log.info("csdn cookie  key is {}", csdnApiProperties.getCookie());
        }
        log.info("MCP服务器启动成功！CSDN文章发布服务已就绪。");
    }
}
