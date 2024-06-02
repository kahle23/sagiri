package sagiri.invoke.handler;

import cn.hutool.core.util.StrUtil;
import kunlun.action.support.AutoActionHandler;
import kunlun.action.support.http.HttpInvokeConfig;
import kunlun.action.support.http.hutool.AbstractScriptBasedHutoolHttpInvokeHandler;
import kunlun.cache.CacheUtils;
import kunlun.data.Dict;
import kunlun.data.json.JsonUtils;
import kunlun.exception.BusinessException;
import kunlun.exception.ExceptionUtils;
import kunlun.exception.util.VerifyUtils;
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
public class DbBasedHutoolHttpInvokeHandler extends AbstractScriptBasedHutoolHttpInvokeHandler
        implements AutoActionHandler {
    private final HandlerConfigImpl handlerConfig = new HandlerConfigImpl();

    @Resource
    private InvokeHttpService invokeHttpService;
    @Resource
    private InvokeLogService invokeLogService;

    @Override
    public String getName() {

        return "invoke-http-db";
    }

    @Override
    public HandlerConfig getConfig() {

        return handlerConfig;
    }

    @Override
    protected void loadConfig(InvokeContext context) {
        String invokeName = context.getInvokeName();
        HttpInvokeConfig config = invokeHttpService.findByName(invokeName);
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
    protected void doInvoke(InvokeContext context) {
        HttpInvokeConfig config = (HttpInvokeConfig) context.getConfig();
        // 获取缓存配置
        Dict cacheConfig = Dict.of(config.getCacheConfig());
        String cacheName = cacheConfig.getString("cacheName");
        String cacheKey = cacheConfig.getString("cacheKey");
        // 处理缓存Key
        cacheKey = StrUtil.isNotBlank(cacheKey)
                ? (String) eval(config.getScriptEngine(), cacheKey, context) : null;
        // 缓存名称 和 缓存Key 不为空，尝试走缓存
        if (StrUtil.isNotBlank(cacheName) && StrUtil.isNotBlank(cacheKey)) {
            Object rawOutput = CacheUtils.get(cacheName, cacheKey, () -> {
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
