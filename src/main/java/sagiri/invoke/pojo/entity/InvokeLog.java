package sagiri.invoke.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 调用日志相关表
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_invoke_log")
public class InvokeLog implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型：1 Http，2 SQL，3 脚本
     */
    private Integer type;
    /**
     * 调用名称
     */
    private String invokeName;
    /**
     * 发生时间
     */
    private Date time;
    /**
     * 期望的类型
     */
    private String expectedClass;
    /**
     * 原始入参
     */
    private String rawInput;
    /**
     * 对应的配置信息
     */
    private String config;
    /**
     * 转换后的入参
     */
    private String convertedInput;
    /**
     * 原始出参
     */
    private String rawOutput;
    /**
     * 转换后的出参
     */
    private String convertedOutput;
    /**
     * 发生的错误信息
     */
    private String error;
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
