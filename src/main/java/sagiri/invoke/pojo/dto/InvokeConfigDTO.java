package sagiri.invoke.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 调用相关配置表 DTO对象
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvokeConfigDTO implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 配置名称
     */
    private String name;
    /**
     * 配置描述
     */
    private String description;
    /**
     * 配置内容（一般情况下都是JSON字符串）
     */
    private String content;
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
