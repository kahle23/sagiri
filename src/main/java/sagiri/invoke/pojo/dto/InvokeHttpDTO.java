package sagiri.invoke.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Http调用配置表 DTO对象
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvokeHttpDTO implements Serializable {

    /**
     * 主键id
     */
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
     * 字符集
     */
    private String charset;
    /**
     * 方法：1 get，2 post，3 put，4 delete
     */
    private Integer method;
    /**
     * URL地址
     */
    private String url;
    /**
     * 请求头（JSON字符串）
     */
    private String headers;
    /**
     * 入参类型：1 no content，2 form-www，3 form-data，4 json
     */
    private Integer inputType;
    /**
     * 表单类参数（JSON字符串）
     */
    private String parameters;
    /**
     * 文本类入参转换脚本
     */
    private String body;
    /**
     * 入参校验规则（JSON字符串）
     */
    private String inputValidations;
    /**
     * 出参类型：1 no content，2 raw data，3 json
     */
    private Integer outputType;
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
