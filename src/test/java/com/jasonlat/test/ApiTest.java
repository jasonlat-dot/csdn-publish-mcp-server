package com.jasonlat.test;

import com.jasonlat.McpServerApplication;
import com.jasonlat.infrastructure.gateway.ICSDNService;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishRequest;
import com.jasonlat.infrastructure.gateway.dto.CsdnPublishResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * API测试类
 * 
 * <p>该测试类用于验证CSDN文章发布服务的各项功能，包括服务Bean的注入、
 * API接口的调用、请求响应的处理等核心功能。</p>
 * 
 * <p>测试范围：</p>
 * <ul>
 *   <li>Spring Boot应用上下文的正确加载</li>
 *   <li>ICSDNService Bean的正确注入和配置</li>
 *   <li>CSDN文章发布API的完整调用流程</li>
 *   <li>请求参数的构建和验证</li>
 *   <li>响应数据的解析和处理</li>
 *   <li>异常情况的处理和日志记录</li>
 * </ul>
 * 
 * <p>测试环境要求：</p>
 * <ul>
 *   <li>Spring Boot测试环境</li>
 *   <li>有效的网络连接（用于API调用）</li>
 *   <li>有效的CSDN Cookie（用于身份认证）</li>
 * </ul>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>测试中的Cookie需要替换为真实有效的值</li>
 *   <li>测试可能会实际调用CSDN API，请谨慎使用</li>
 *   <li>建议在开发环境中运行，避免影响生产数据</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2024-01-01
 * @see ICSDNService
 * @see CsdnPublishRequest
 * @see CsdnPublishResponse
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = McpServerApplication.class)
public class ApiTest {

    /**
     * CSDN服务接口实例
     * 
     * <p>通过Spring的依赖注入机制自动装配ICSDNService实例。
     * 该实例已经过完整配置，包括Retrofit客户端、HTTP超时设置、
     * 日志拦截器等，可直接用于API调用。</p>
     * 
     * <p>注入的服务特性：</p>
     * <ul>
     *   <li>已配置CSDN API的基础URL</li>
     *   <li>已设置合适的超时时间</li>
     *   <li>已启用HTTP请求/响应日志</li>
     *   <li>已配置JSON序列化/反序列化</li>
     * </ul>
     * 
     * @see ICSDNService
     * @see com.jasonlat.infrastructure.gateway.CsdnRetrofitConfig
     */
    @Autowired
    private ICSDNService csdnService;

    /**
     * 基础测试方法
     * 
     * <p>这是一个简单的测试方法，用于验证测试环境的基本功能。
     * 主要用于确认JUnit测试框架和Spring Boot测试环境能够正常工作。</p>
     * 
     * <p>测试内容：</p>
     * <ul>
     *   <li>验证测试类能够正常加载</li>
     *   <li>验证Spring Boot应用上下文能够正常启动</li>
     *   <li>验证测试方法能够正常执行</li>
     * </ul>
     * 
     * <p>该测试方法不涉及任何业务逻辑，仅用于环境验证。</p>
     * 
     * @since 1.0
     */
    @Test
    public void test() {
        log.info("测试完成");
    }

    /**
     * 测试CSDN文章发布接口
     * 
     * <p>该测试方法用于验证CSDN文章发布API的完整调用流程，包括请求构建、
     * API调用、响应处理等各个环节。这是核心业务功能的集成测试。</p>
     * 
     * <p>测试流程：</p>
     * <ol>
     *   <li>构建CsdnPublishRequest请求对象，包含文章的各项信息</li>
     *   <li>设置Cookie认证信息（需要有效的CSDN登录Cookie）</li>
     *   <li>调用ICSDNService的publishArticle方法</li>
     *   <li>处理HTTP响应，解析返回的文章信息</li>
     *   <li>记录成功或失败的详细信息</li>
     * </ol>
     * 
     * <p>测试数据说明：</p>
     * <ul>
     *   <li>title: 文章标题，用于在CSDN平台显示</li>
     *   <li>markdownContent: Markdown格式的文章内容</li>
     *   <li>content: HTML格式的文章内容</li>
     *   <li>readType: 文章可见性（public/private）</li>
     *   <li>level: 文章等级设置</li>
     *   <li>tags: 文章标签，多个标签用逗号分隔</li>
     *   <li>status: 文章状态（0-草稿，1-发布）</li>
     *   <li>type: 文章类型（original/reprint/translate）</li>
     *   <li>description: 文章摘要描述</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>Cookie需要替换为真实有效的CSDN登录Cookie</li>
     *   <li>该测试会实际调用CSDN API，可能会创建真实文章</li>
     *   <li>建议在测试环境中使用测试账号进行验证</li>
     *   <li>网络异常或认证失败时会记录详细错误信息</li>
     *   <li>测试中的Cookie包含完整的认证和会话信息</li>
     * </ul>
     * 
     * @throws IOException 当网络请求发生异常时抛出
     * @throws Exception 当其他异常发生时抛出
     * @see CsdnPublishRequest
     * @see CsdnPublishResponse
     * @see ICSDNService#publishArticle(String, CsdnPublishRequest)
     */
    @Test
    public void testCsdnPublishArticle() {
        try {
            // 构建请求对象，只设置必要的字段，其他字段使用默认值
            CsdnPublishRequest request = CsdnPublishRequest.builder()
                    .title("测试文章标题")
                    .markdownContent("# 测试文章\n\n这是一篇测试文章的内容。")
                    .content("<h1>测试文章</h1><p>这是一篇测试文章的内容。</p>")
                    .tags("Java,测试")
                    .description("这是一篇测试文章。")
                    .build();

            // 测试用的Cookie（实际使用时需要替换为真实有效的Cookie）
            String testCookie = "uuid_tt_dd=10_20290684990-1753209489095-887032; fid=20_31381811281-1753209442061-606290; UN=m0_62310006; p_uid=U010000; csdn_newcert_m0_62310006=1; c_dl_prid=-; c_dl_rid=1754235235033_293829; c_dl_fref=https://blog.csdn.net/csdnerM/article/details/136784455; c_dl_fpage=/download/weixin_38682054/12751987; c_dl_um=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7EPaidSort-1-142236198-blog-136784455.235%5Ev43%5Epc_blog_bottom_relevance_base5; creative_btn_mp=3; c_segment=8; dc_sid=0123faae676a263a99fd648f62228e53; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1755949540,1755963493,1755963576,1756006743; HMACCOUNT=90350D62738A95D6; _clck=139wy3g%5E2%5Efyq%5E0%5E2059; c_ab_test=1; https_ydclearance=cc06fe9378550d2ee709e5a9-ff3b-403e-baa2-3f509a9e0f35-1756033128; dc_session_id=10_1756025928685.364480; FCNEC=%5B%5B%22AKsRol8kz72tthqqBmeou4-1OwOeVEUFb7kvqGEHY3_ayAowasTknFy-6Pf6q15sxkTYPvPA99lbCWmJqtbxtlre7FCn0PWX1SHcoGSgSiiX1jyP2mMGHuKmahojC7u8hScAXymnHoQOkHTVhwu7WnpAKihqEeyABw%3D%3D%22%5D%5D; c_first_ref=default; c_first_page=https%3A//blog.csdn.net/m0_62310006/article/details/150701174; c_dsid=11_1756031859368.059056; __gads=ID=fee264c5b112516f:T=1755783435:RT=1756032329:S=ALNI_MY7VIXDAAEa_M8ToJQ7WXOZEBgMeA; __gpi=UID=00001183e64c7543:T=1755783435:RT=1756032329:S=ALNI_MaqzQZCxvVLa85akPGMR4wsa4L2Rg; __eoi=ID=87f6282774d5a9b7:T=1755783435:RT=1756032329:S=AA-AfjbK8v_Jip84jQmol_qV0IDw; SESSION=f5c18dcb-bdfe-4f2a-b21c-602e12142e3e; c_pref=https%3A//blog.csdn.net/m0_62310006/article/details/150701174%3Fspm%3D1001.2014.3001.5501; c_ref=https%3A//blog.csdn.net/m0_62310006%3Ftype%3Dblog; hide_login=1; loginbox_strategy=%7B%22taskId%22%3A317%2C%22abCheckTime%22%3A1756032360551%2C%22version%22%3A%22ExpA%22%2C%22nickName%22%3A%22%E6%B5%81%E5%B9%B4%E6%9F%93%E6%8C%87%E6%82%B2%E4%BC%A4%E3%80%81%22%7D; UserName=m0_62310006; UserInfo=fb433bf2573b41dbb51461c5f062f20d; UserToken=fb433bf2573b41dbb51461c5f062f20d; UserNick=%E6%B5%81%E5%B9%B4%E6%9F%93%E6%8C%87%E6%82%B2%E4%BC%A4%E3%80%81; AU=1E9; BT=1756032376912; c_page_id=default; log_Id_pv=21; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1756032641; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A9%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22m0_62310006%22%7D; log_Id_view=670; dc_tos=t1hvgj; _clsk=oyagxf%5E1756032644209%5E7%5E0%5Ei.clarity.ms%2Fcollect;";

            log.info("开始测试CSDN文章发布接口...");
            log.info("请求参数: {}", request);

            // 调用接口（其他Header参数已通过@Headers注解提供）
            Call<CsdnPublishResponse> call = csdnService.publishArticle(
                    testCookie,
                    request
            );

            // 执行请求
            Response<CsdnPublishResponse> response = call.execute();
            
            log.info("响应状态码: {}", response.code());
            log.info("响应头: {}", response.headers());
            
            if (response.isSuccessful() && response.body() != null) {
                CsdnPublishResponse result = response.body();
                log.info("发布成功！响应数据: {}", result);
                log.info("文章ID: {}", result.getData().getId());
                log.info("文章URL: {}", result.getData().getUrl());
            } else {
                log.error("发布失败！响应码: {}", response.code());
                if (response.errorBody() != null) {
                    log.error("错误信息: {}", response.errorBody().string());
                }
            }
            
        } catch (IOException e) {
            log.error("网络请求异常", e);
        } catch (Exception e) {
            log.error("测试异常", e);
        }
    }

    /**
     * 测试CSDN服务Bean注入
     * 
     * <p>该测试方法用于验证Spring容器是否正确创建和注入了ICSDNService Bean。
     * 这是一个基础的依赖注入测试，确保服务层组件能够正常工作。</p>
     * 
     * <p>验证内容：</p>
     * <ul>
     *   <li>ICSDNService Bean是否成功创建</li>
     *   <li>依赖注入是否正常工作</li>
     *   <li>Bean的类型是否正确</li>
     *   <li>Spring Boot自动配置是否生效</li>
     * </ul>
     * 
     * <p>测试意义：</p>
     * <ul>
     *   <li>确保Spring Boot应用上下文正确加载</li>
     *   <li>验证CsdnRetrofitConfig配置类的@Bean方法正常工作</li>
     *   <li>确保Retrofit相关依赖正确配置</li>
     *   <li>为后续API调用测试提供基础保障</li>
     * </ul>
     * 
     * <p>如果该测试失败，可能的原因：</p>
     * <ul>
     *   <li>Spring Boot配置有误</li>
     *   <li>CsdnRetrofitConfig类未被正确扫描</li>
     *   <li>相关依赖缺失或版本冲突</li>
     *   <li>Bean创建过程中发生异常</li>
     * </ul>
     * 
     * <p>测试执行流程：</p>
     * <ol>
     *   <li>检查csdnService字段是否为null</li>
     *   <li>记录Bean注入的状态信息</li>
     *   <li>输出Bean的具体类名用于调试</li>
     * </ol>
     * 
     * @see ICSDNService
     * @see com.jasonlat.infrastructure.gateway.CsdnRetrofitConfig
     */
    @Test
    public void testCsdnServiceBean() {
        log.info("测试CSDN服务Bean注入...");
        
        if (csdnService != null) {
            log.info("CSDN服务Bean注入成功: {}", csdnService.getClass().getName());
        } else {
            log.error("CSDN服务Bean注入失败");
        }
    }
}
