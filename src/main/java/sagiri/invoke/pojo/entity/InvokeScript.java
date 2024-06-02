package sagiri.invoke.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 脚本调用配置表
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_invoke_script")
public class InvokeScript implements Serializable {

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
     * 脚本名称
     */
    private String scriptName;
    /**
     * 脚本描述
     */
    private String scriptDescription;
    /**
     * 脚本引擎名称
     */
    private String scriptEngine;
    /**
     * 脚本内容
     */
    private String scriptContent;
    /**
     * 入参校验规则（JSON字符串）
     */
    private String inputValidations;
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

}
