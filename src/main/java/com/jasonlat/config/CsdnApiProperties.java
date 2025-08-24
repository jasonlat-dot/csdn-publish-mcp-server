package com.jasonlat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CSDN API配置属性类
 * 
 * <p>该类用于从配置文件中读取CSDN API相关的配置信息，
 * 通过Spring Boot的@ConfigurationProperties注解实现配置属性的自动绑定。</p>
 * 
 * <p>配置前缀：csdn.api</p>
 * 
 * <p>支持的配置项：</p>
 * <ul>
 *   <li>csdn.api.cookie - CSDN登录Cookie信息</li>
 *   <li>csdn.api.categories - 默认文章分类配置</li>
 * </ul>
 * 
 * <p>配置文件示例（application.yml）：</p>
 * <pre>{@code
 * csdn:
 *   api:
 *     cookie: "your_csdn_cookie_here"
 *     categories: 'Java'
 * }</pre>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>统一管理CSDN API的认证信息</li>
 *   <li>配置默认的文章分类信息</li>
 *   <li>支持不同环境的配置切换</li>
 *   <li>避免硬编码敏感信息</li>
 * </ul>
 * 
 * <p>安全注意事项：</p>
 * <ul>
 *   <li>Cookie信息包含敏感的认证数据，请妥善保管</li>
 *   <li>建议在生产环境中使用环境变量或加密配置</li>
 *   <li>定期更新Cookie信息以确保有效性</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@ConfigurationProperties(prefix = "csdn.api")
@Component
public class CsdnApiProperties {
    /**
     * CSDN API认证Cookie
     * 
     * <p>用于CSDN API调用时的身份认证，包含用户的登录状态信息。
     * 该Cookie需要从浏览器中获取，确保用户已登录CSDN平台。</p>
     * 
     * <p>获取方式：</p>
     * <ol>
     *   <li>登录CSDN官网</li>
     *   <li>打开浏览器开发者工具</li>
     *   <li>在Network标签页中查看请求头</li>
     *   <li>复制Cookie字段的完整值</li>
     * </ol>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>Cookie具有时效性，过期后需要重新获取</li>
     *   <li>包含敏感信息，请勿泄露给他人</li>
     *   <li>建议定期更新以确保API调用的稳定性</li>
     * </ul>
     */
    private String cookie;

    /**
     * 默认文章分类配置
     * 
     * <p>JSON格式的字符串，定义文章发布时的默认分类信息。
     * 用于简化文章发布流程，避免每次都手动指定分类。</p>
     * 
     * <p>格式示例：</p>
     * <pre>{@code
     * [{"categoryId":1,"categoryName":"Java"}]
     * }</pre>
     * 
     * <p>常用分类ID参考：</p>
     * <ul>
     *   <li>1 - Java</li>
     *   <li>2 - Python</li>
     *   <li>3 - 前端</li>
     *   <li>4 - 数据库</li>
     *   <li>5 - 运维</li>
     * </ul>
     * 
     * <p>注意：具体的分类ID可能因CSDN平台更新而变化，
     * 建议通过CSDN编辑器查看最新的分类信息。</p>
     */
    private String categories;

    /**
     * 获取CSDN API认证Cookie
     * 
     * @return Cookie字符串，用于API认证
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * 设置CSDN API认证Cookie
     * 
     * @param cookie Cookie字符串，包含完整的认证信息
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取默认文章分类配置
     * 
     * @return JSON格式的分类配置字符串
     */
    public String getCategories() {
        return categories;
    }

    /**
     * 设置默认文章分类配置
     * 
     * @param categories JSON格式的分类配置字符串
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }
}
