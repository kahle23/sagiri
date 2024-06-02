package sagiri.invoke.handler;

import kunlun.action.support.AutoActionHandler;
import kunlun.action.support.jdbc.JdbcInvokeConfig;
import kunlun.action.support.jdbc.spring.AbstractScriptBasedJdbcTemplateInvokeHandler;
import kunlun.data.json.JsonUtils;
import kunlun.exception.BusinessException;
import kunlun.exception.ExceptionUtils;
import kunlun.exception.util.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import sagiri.invoke.pojo.form.InvokeLogAddForm;
import sagiri.invoke.service.InvokeJdbcService;
import sagiri.invoke.service.InvokeLogService;

import javax.annotation.Resource;
import java.util.Date;

import static kunlun.common.constant.Numbers.TWO;
import static kunlun.data.json.JsonFormat.PRETTY_FORMAT;

/**
 * 基于 JdbcTemplate 和 数据库 的 Jdbc 调用工具.
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Component
public class DbBasedJdbcInvokeHandler extends AbstractScriptBasedJdbcTemplateInvokeHandler
        implements AutoActionHandler {
    private final HandlerConfigImpl handlerConfig = new HandlerConfigImpl();

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private InvokeJdbcService invokeJdbcService;
    @Resource
    private InvokeLogService invokeLogService;

    @Override
    public String getName() {

        return "invoke-jdbc-db";
    }

    @Override
    public HandlerConfig getConfig() {

        return handlerConfig;
    }

    @Override
    protected NamedParameterJdbcTemplate getJdbcTemplate() {

        return namedParameterJdbcTemplate;
    }

    @Override
    protected void loadConfig(InvokeContext context) {
        String invokeName = context.getInvokeName();
        JdbcInvokeConfig config = invokeJdbcService.findByName(invokeName);
        VerifyUtils.notNull(config, "根据调用名称无法找到对应的配置，配置无效或未配置！");
        context.setConfig(config);
    }

    @Override
    protected void throwException(boolean validate, String message) {
        if (!validate) {
            throw new BusinessException(message);
        }
    }

    @Override
    protected void recordLog(InvokeContext context) {
        InvokeLogAddForm form = new InvokeLogAddForm();
        try {
            form.setType(TWO);
            form.setInvokeName(context.getInvokeName());
            form.setTime(new Date());
            form.setExpectedClass(context.getExpectedClass().getName());
            form.setRawInput(JsonUtils.toJsonString(context.getRawInput(), PRETTY_FORMAT));
            form.setConfig(JsonUtils.toJsonString(context.getConfig(), PRETTY_FORMAT));
            form.setConvertedInput(JsonUtils.toJsonString(context.getConvertedInput(), PRETTY_FORMAT));
            form.setRawOutput(JsonUtils.toJsonString(context.getRawOutput(), PRETTY_FORMAT));
            form.setConvertedOutput(JsonUtils.toJsonString(context.getConvertedOutput(), PRETTY_FORMAT));
            Throwable error = context.getError();
            if (error != null) {
                form.setError(ExceptionUtils.toString(error));
            }
            invokeLogService.add(form);
        }
        catch (Exception e) {
            log.error("保存调用日志失败！", e);
            log.warn("待保存的调用日志信息为：\n{}", JsonUtils.toJsonString(form));
        }
    }

}
