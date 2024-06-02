package sagiri.invoke.pojo.form;

import kunlun.data.validation.support.javax.group.Create;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 调用日志相关表 数据增加 Form
 * @author Sagiri
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvokeLogAddForm implements Serializable {

    /**
     * 类型：1 Http，2 SQL，3 脚本
     */
    @NotNull(message = "调用日志类型不能为空！", groups = {Create.class})
    private Integer type;
    /**
     * 调用名称
     */
    @NotBlank(message = "调用名称不能为空！", groups = {Create.class})
    private String invokeName;
    /**
     * 发生时间
     */
    @NotNull(message = "发生时间不能为空！", groups = {Create.class})
    private Date time;
    /**
     * 期望的类型
     */
    @NotBlank(message = "期望的类型不能为空！", groups = {Create.class})
    private String expectedClass;
    /**
     * 原始入参
     */
    @NotBlank(message = "原始入参不能为空！", groups = {Create.class})
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

}
