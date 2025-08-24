package com.jasonlat.infrastructure.gateway.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * CSDN文章发布请求DTO
 * 
 * <p>该类用于封装发布文章到CSDN平台的所有请求参数。
 * 包含文章的基本信息、内容、发布设置、分类标签等完整信息。</p>
 * 
 * <p>部分字段已设置合理的默认值，简化使用：</p>
 * <ul>
 *   <li>readType: 默认为"public"（公开）</li>
 *   <li>level: 默认为"0"（普通文章）</li>
 *   <li>status: 默认为0（草稿状态）</li>
 *   <li>type: 默认为"original"（原创文章）</li>
 *   <li>source: 默认为"pc_mdeditor"（PC端Markdown编辑器）</li>
 *   <li>coverType: 默认为1（默认封面）</li>
 *   <li>isNew: 默认为1（新文章）</li>
 *   <li>pubStatus: 默认为"publish"（发布状态）</li>
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
public class CsdnPublishRequest {

    /**
     * 文章标题
     */
    private String title;

    /**
     * Markdown格式的文章内容
     */
    @SerializedName("markdowncontent")
    private String markdownContent;

    /**
     * HTML格式的文章内容
     */
    private String content;

    /**
     * 阅读类型：public-公开，private-私有
     * 默认值：public（公开文章）
     */
    @SerializedName("readType")
    @Builder.Default
    private String readType = "public";

    /**
     * 文章等级：0-普通文章，1-优质文章
     * 默认值：0（普通文章）
     */
    @Builder.Default
    private String level = "0";

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 文章状态：0-草稿，1-发布
     * 默认值：0（草稿状态，可先保存为草稿再发布）
     */
    @Builder.Default
    private Integer status = 0;

    /**
     * 文章分类
     */
    private String categories;

    /**
     * 文章类型：original-原创，reprint-转载，translate-翻译
     * 默认值：original（原创文章）
     */
    @Builder.Default
    private String type = "original";

    /**
     * 原文链接（转载或翻译时使用）
     */
    @SerializedName("original_link")
    private String originalLink;

    /**
     * 授权状态：是否授权转载
     * 默认值：false（不授权转载）
     */
    @SerializedName("authorized_status")
    @Builder.Default
    private Boolean authorizedStatus = false;

    /**
     * 文章描述
     */
    @SerializedName("Description")
    private String description;

    /**
     * 资源URL
     */
    @SerializedName("resource_url")
    private String resourceUrl;

    /**
     * 是否自动保存：1-不自动保存，0-自动保存
     * 默认值：1（不自动保存，避免意外覆盖）
     */
    @SerializedName("not_auto_saved")
    @Builder.Default
    private String notAutoSaved = "1";

    /**
     * 来源：pc_mdeditor-PC端Markdown编辑器，mobile-移动端
     * 默认值：pc_mdeditor（PC端Markdown编辑器）
     */
    @Builder.Default
    private String source = "pc_mdeditor";

    /**
     * 封面图片列表：文章封面图片URL列表
     * 默认值：空列表（使用系统默认封面）
     */
    @SerializedName("cover_images")
    @Builder.Default
    private List<String> coverImages = new ArrayList<>();

    /**
     * 封面类型：1-默认封面，2-自定义封面
     * 默认值：1（使用默认封面）
     */
    @SerializedName("cover_type")
    @Builder.Default
    private Integer coverType = 1;

    /**
     * 是否新文章：1-是新文章，0-编辑已有文章
     * 默认值：1（新文章）
     */
    @SerializedName("is_new")
    @Builder.Default
    private Integer isNew = 1;

    /**
     * 投票ID：关联的投票活动ID
     * 默认值：0（无关联投票）
     */
    @SerializedName("vote_id")
    @Builder.Default
    private Integer voteId = 0;

    /**
     * 资源ID
     */
    @SerializedName("resource_id")
    private String resourceId;

    /**
     * 发布状态：publish-发布，draft-草稿
     * 默认值：publish（发布状态）
     */
    @SerializedName("pubStatus")
    @Builder.Default
    private String pubStatus = "publish";

    /**
     * 同步Git代码：0-不同步，1-同步到Git仓库
     * 默认值：0（不同步到Git）
     */
    @SerializedName("sync_git_code")
    @Builder.Default
    private Integer syncGitCode = 0;
}