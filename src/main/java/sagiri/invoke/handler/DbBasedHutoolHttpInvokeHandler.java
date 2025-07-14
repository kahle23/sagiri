package sagiri.invoke.handler;

import cn.hutool.core.util.StrUtil;
import kunlun.action.invoke.http.HttpInvokeConfig;
import kunlun.action.invoke.http.support.hutool.AbstractScriptBasedHutoolHttpInvokeAction;
import kunlun.action.support.AutoAction;
import kunlun.cache.CacheUtil;
import kunlun.data.Dict;
import kunlun.data.json.JsonUtil;
import kunlun.exception.ExceptionUtil;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.handler.ScriptHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sagiri.invoke.pojo.form.InvokeLogAddForm;
import sagiri.invoke.service.InvokeHttpService;
import sagiri.invoke.service.InvokeLogService;

import javax.annotation.Resource;
import java.util.Date;

import static kunlun.common.constant.Numbers.ONE;
import static kunlun.data.json.JsonFormat.PRETTY_FORMAT;

/**
 * 基于 Hutool 和 数据库 的 Http 调用工具.
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Component
public class DbBasedHutoolHttpInvokeHandler extends AbstractScriptBasedHutoolHttpInvokeAction
        implements AutoAction {

    @Resource
    private InvokeHttpService invokeHttpService;
    @Resource
    private InvokeLogService invokeLogService;
    @Resource
    private ScriptHandler scriptHandler;

    @Override
    public String getName() {

        return "invoke-http-db";
    }

    @Override
    protected ScriptHandler getScriptHandler() {

        return scriptHandler;
    }

    @Override
    protected void loadConfig(InvokeContext context) {
        String invokeName = context.getInvokeName();
        HttpInvokeConfig config = invokeHttpService.findByName(invokeName);
        VerifyUtils.notNull(config, "根据调用名称无法找到对应的配置，配置无效或未配置！");
        context.setConfig(config);
    }

    @Override
    protected void doInvoke(InvokeContext context) {
        HttpInvokeConfig config = (HttpInvokeConfig) context.getConfig();
        // 获取缓存配置
        Dict cacheConfig = Dict.of(config.getCacheConfig());
        String cacheName = cacheConfig.getString("cacheName");
        String cacheKey = cacheConfig.getString("cacheKey");
        // 处理缓存Key
        cacheKey = StrUtil.isNotBlank(cacheKey) ? (String)
                getScriptHandler().eval(config.getScriptEngine(), cacheKey, context) : null;
        // 缓存名称 和 缓存Key 不为空，尝试走缓存
        if (StrUtil.isNotBlank(cacheName) && StrUtil.isNotBlank(cacheKey)) {
            Object rawOutput = CacheUtil.get(cacheName, cacheKey, () -> {
                DbBasedHutoolHttpInvokeHandler.super.doInvoke(context);
                return context.getRawOutput();
            });
            context.setRawOutput(rawOutput);
        }
        // 不走缓存
        else { super.doInvoke(context); }
    }

    @Override
    protected void recordLog(InvokeContext context) {
        InvokeLogAddForm form = new InvokeLogAddForm();
        try {
            form.setType(ONE);
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
