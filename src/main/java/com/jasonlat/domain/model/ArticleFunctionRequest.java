package com.jasonlat.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.jasonlat.type.utils.MarkdownConverter;
import lombok.Data;

/**
 * CSDN文章发布请求领域模型
 * 
 * <p>该类是基于DDD（领域驱动设计）架构的领域模型，
 * 用于封装文章发布请求的所有必要信息。</p>
 * 
 * <p>模型特性：</p>
 * <ul>
 *   <li>位于领域层（Domain Layer），表示核心业务概念</li>
 *   <li>使用Jackson注解支持JSON序列化/反序列化</li>
 *   <li>集成Spring AI工具参数映射</li>
 *   <li>提供Markdown到HTML的自动转换功能</li>
 *   <li>遵循不可变对象设计原则</li>
 * </ul>
 * 
 * <p>JSON配置说明：</p>
 * <ul>
 *   <li>@JsonInclude(NON_NULL) - 忽略null值字段</li>
 *   <li>@JsonProperty - 定义JSON字段名和必填属性</li>
 *   <li>@JsonPropertyDescription - 提供字段描述信息</li>
 * </ul>
 * 
 * <p>数据验证：</p>
 * <ul>
 *   <li>所有字段均标记为required=true</li>
 *   <li>AI工具调用时会自动验证必填字段</li>
 *   <li>支持Spring Validation注解扩展</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>AI助手通过MCP协议传递文章发布参数</li>
 *   <li>领域服务接收和处理文章发布请求</li>
 *   <li>端口适配器进行数据转换</li>
 * </ul>
 * 
 * <p>字段说明：</p>
 * <ul>
 *   <li>title - 文章标题，用于SEO和用户识别</li>
 *   <li>markdowncontent - Markdown格式的文章内容</li>
 *   <li>tags - 文章标签，用于分类和搜索</li>
 *   <li>Description - 文章摘要，用于列表展示</li>
 * </ul>
 * 
 * @author jasonlat
 * @version 1.0
 * @since 2025-08-24
 * @see ArticleFunctionResponse
 * @see MarkdownConverter
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFunctionRequest {

    /**
     * 文章标题
     * 
     * <p>文章的主标题，用于在CSDN平台上显示和SEO优化。</p>
     * 
     * <p>要求：</p>
     * <ul>
     *   <li>必填字段，不能为空</li>
     *   <li>建议长度：10-50个字符</li>
     *   <li>应具有吸引力和描述性</li>
     *   <li>避免使用特殊符号和敏感词汇</li>
     * </ul>
     * 
     * <p>示例：</p>
     * <ul>
     *   <li>"Spring Boot集成Redis实现缓存优化"</li>
     *   <li>"深入理解Java并发编程原理"</li>
     *   <li>"Vue3组件设计模式最佳实践"</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "title")
    @JsonPropertyDescription("文章标题")
    private String title;

    /**
     * Markdown格式的文章内容
     * 
     * <p>使用Markdown语法编写的文章正文内容，
     * 将通过MarkdownConverter自动转换为HTML格式。</p>
     * 
     * <p>支持的Markdown语法：</p>
     * <ul>
     *   <li>标题：# ## ### 等</li>
     *   <li>代码块：```language 和 `inline code`</li>
     *   <li>列表：- 和 1. 等</li>
     *   <li>链接：[text](url) 和图片：![alt](url)</li>
     *   <li>表格、引用、加粗、斜体等</li>
     * </ul>
     * 
     * <p>内容要求：</p>
     * <ul>
     *   <li>必填字段，不能为空</li>
     *   <li>建议长度：500字符以上</li>
     *   <li>结构清晰，层次分明</li>
     *   <li>代码示例应完整可运行</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>避免使用HTML标签，优先使用Markdown语法</li>
     *   <li>图片链接应使用稳定的外链地址</li>
     *   <li>代码块应指定正确的语言类型</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "markdowncontent")
    @JsonPropertyDescription("文章内容")
    private String markdowncontent;

    /**
     * 文章标签
     * 
     * <p>用于文章分类和搜索的标签列表，
     * 多个标签之间使用英文逗号分隔。</p>
     * 
     * <p>标签规范：</p>
     * <ul>
     *   <li>必填字段，至少包含1个标签</li>
     *   <li>建议数量：3-5个标签</li>
     *   <li>使用英文逗号分隔："Java,Spring Boot,Redis"</li>
     *   <li>标签名称应准确反映文章内容</li>
     * </ul>
     * 
     * <p>常用技术标签：</p>
     * <ul>
     *   <li>编程语言：Java, Python, JavaScript, Go</li>
     *   <li>框架技术：Spring Boot, Vue, React, Django</li>
     *   <li>数据库：MySQL, Redis, MongoDB, PostgreSQL</li>
     *   <li>工具平台：Docker, Kubernetes, Git, Linux</li>
     * </ul>
     * 
     * <p>示例：</p>
     * <ul>
     *   <li>"Java,Spring Boot,微服务,架构设计"</li>
     *   <li>"Vue3,前端开发,组件化,TypeScript"</li>
     *   <li>"数据结构,算法,面试,LeetCode"</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "tags")
    @JsonPropertyDescription("文章标签，英文逗号隔开")
    private String tags;

    /**
     * 文章描述/摘要
     * 
     * <p>文章的简要描述或摘要信息，
     * 用于在文章列表、搜索结果和社交分享中显示。</p>
     * 
     * <p>描述要求：</p>
     * <ul>
     *   <li>必填字段，不能为空</li>
     *   <li>建议长度：50-200个字符</li>
     *   <li>应简洁明了地概括文章主要内容</li>
     *   <li>避免重复标题内容</li>
     * </ul>
     * 
     * <p>写作技巧：</p>
     * <ul>
     *   <li>突出文章的核心价值和亮点</li>
     *   <li>包含关键技术词汇提升搜索匹配</li>
     *   <li>使用吸引人的语言激发阅读兴趣</li>
     *   <li>可以提及解决的问题或达成的效果</li>
     * </ul>
     * 
     * <p>示例：</p>
     * <ul>
     *   <li>"详细介绍Spring Boot集成Redis的配置方法和最佳实践，包含完整代码示例和性能优化技巧。"</li>
     *   <li>"从零开始构建Vue3组件库，涵盖设计原则、开发流程和发布部署的完整指南。"</li>
     * </ul>
     */
    @JsonProperty(required = true, value = "Description")
    @JsonPropertyDescription("文章简述")
    private String Description;

    /**
     * 获取HTML格式的文章内容
     * 
     * <p>将Markdown格式的文章内容转换为HTML格式，
     * 用于提交到CSDN API进行文章发布。</p>
     * 
     * <p>转换特性：</p>
     * <ul>
     *   <li>自动将Markdown语法转换为对应的HTML标签</li>
     *   <li>保持代码块的语法高亮信息</li>
     *   <li>正确处理列表、表格、链接等复杂结构</li>
     *   <li>确保生成的HTML符合CSDN平台要求</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>CsdnPort适配器构建API请求时调用</li>
     *   <li>确保内容格式与CSDN编辑器兼容</li>
     *   <li>支持富文本内容的正确显示</li>
     * </ul>
     * 
     * @return HTML格式的文章内容字符串
     * @see MarkdownConverter#convertMdToHtml(String)
     */
    public String getContent() {
        return MarkdownConverter.convertMdToHtml(markdowncontent);
    }

}
