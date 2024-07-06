package sagiri.invoke.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * AI调用配置表的数据库实体.
 *
 * @author Sagiri
 * @since 2024-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_invoke_ai")
public class InvokeAi implements Serializable {

    /* (Start) This will be overridden, please do not modify. */
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 调用名称
     */
    private String name;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 脚本引擎名称
     */
    private String scriptEngine;
    /**
     * 模板引擎名称
     */
    private String rendererName;
    /**
     * 入参校验规则（JSON字符串）
     */
    private String inputValidations;
    /**
     * 入参转换脚本
     */
    private String inputConversionScript;
    /**
     * AI的系统的提示词
     */
    private String systemPrompt;
    /**
     * AI的用户的提示词
     */
    private String userPrompt;
    /**
     * AI的工具的提示词
     */
    private String toolPrompt;
    /**
     * AI处理器的名称
     */
    private String handlerName;
    /**
     * AI处理器方法名
     */
    private String methodName;
    /**
     * 出参转换脚本
     */
    private String outputConversionScript;
    /**
     * 出参校验规则（JSON字符串）
     */
    private String outputValidations;
    /**
     * 缓存配置（JSON字符串，Java对象为 Map）
     */
    private String cacheConfig;
    /**
     * 配置ID（otherConfigs中填充的内容）
     */
    private Long configId;
    /**
     * 平台信息：0 未知
     */
    private String platform;
    /**
     * 租户ID（0表示无租户）
     */
    private Long tenantId;
    /**
     * 创建者
     */
    private Long createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改者
     */
    private Long modifyUser;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 删除状态：0 未删除，1 已删除
     */
    private Integer deleteStatus;
    /* (End) This will be overridden, please do not modify. */

}
