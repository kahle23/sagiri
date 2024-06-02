package sagiri.invoke.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Jdbc调用配置表
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_invoke_jdbc")
public class InvokeJdbc implements Serializable {

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
    private String input;
    /**
     * 执行类型：2 更新，3 批量更新，4 单值查询，5 单对象查询，6 对象数组查询，7 对象数组分页查询，8 对象数组分页滚动查询
     */
    private Integer executeType;
    /**
     * 要执行的SQL
     */
    @TableField("`sql`")
    private String sql;
    /**
     * 出参转换脚本
     */
    private String output;
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
    @TableField(fill = FieldFill.INSERT)
    private String platform;
    /**
     * 租户ID（0表示无租户）
     */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long modifyUser;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     * 删除状态：0 未删除，1 已删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteStatus;

}
