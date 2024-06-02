package sagiri.invoke.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 调用日志相关表 DTO对象
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvokeLogDTO implements Serializable {

    /**
     * 主键ID
     */
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
