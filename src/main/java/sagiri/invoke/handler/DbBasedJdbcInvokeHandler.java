package sagiri.invoke.handler;

import kunlun.action.invoke.jdbc.JdbcInvokeConfig;
import kunlun.action.invoke.jdbc.support.spring.AbstractScriptBasedJdbcTemplateInvokeAction;
import kunlun.action.support.AutoAction;
import kunlun.data.json.JsonUtil;
import kunlun.exception.ExceptionUtil;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.handler.ScriptHandler;
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
public class DbBasedJdbcInvokeHandler extends AbstractScriptBasedJdbcTemplateInvokeAction
        implements AutoAction {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private InvokeJdbcService invokeJdbcService;
    @Resource
    private InvokeLogService invokeLogService;
    @Resource
    private ScriptHandler scriptHandler;

    @Override
    public String getName() {

        return "invoke-jdbc-db";
    }

    @Override
    protected ScriptHandler getScriptHandler() {

        return scriptHandler;
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
    protected void recordLog(InvokeContext context) {
        InvokeLogAddForm form = new InvokeLogAddForm();
        try {
            form.setType(TWO);
            form.setInvokeName(context.getInvokeName());
            form.setTime(new Date());
            form.setRawInput(JsonUtil.toJsonString(context.getRawInput(), PRETTY_FORMAT));
            form.setConfig(JsonUtil.toJsonString(context.getConfig(), PRETTY_FORMAT));
            form.setConvertedInput(JsonUtil.toJsonString(context.getConvertedInput(), PRETTY_FORMAT));
            form.setRawOutput(JsonUtil.toJsonString(context.getRawOutput(), PRETTY_FORMAT));
            form.setConvertedOutput(JsonUtil.toJsonString(context.getConvertedOutput(), PRETTY_FORMAT));
            Throwable error = context.getError();
            if (error != null) {
                form.setError(ExceptionUtil.toString(error));
            }
            invokeLogService.add(form);
        }
        catch (Exception e) {
            log.error("保存调用日志失败！", e);
            log.warn("待保存的调用日志信息为：\n{}", JsonUtil.toJsonString(form));
        }
    }

}
