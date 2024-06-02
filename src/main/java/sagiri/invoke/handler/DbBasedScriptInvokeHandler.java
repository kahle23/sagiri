package sagiri.invoke.handler;

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.log.XxlJobLogger;
import kunlun.action.support.AbstractInvokeActionHandler;
import kunlun.action.support.AutoActionHandler;
import kunlun.action.support.script.AbstractScriptBasedScriptInvokeHandler;
import kunlun.action.support.script.ScriptInvokeConfig;
import kunlun.cache.CacheUtils;
import kunlun.data.Dict;
import kunlun.data.bean.BeanUtils;
import kunlun.data.json.JsonUtils;
import kunlun.exception.BusinessException;
import kunlun.exception.ExceptionUtils;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import sagiri.invoke.pojo.form.InvokeLogAddForm;
import sagiri.invoke.service.InvokeLogService;
import sagiri.invoke.service.InvokeScriptService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static kunlun.common.constant.Numbers.THREE;
import static kunlun.data.json.JsonFormat.PRETTY_FORMAT;

/**
 * 基于 脚本引擎 和 数据库 的 脚本调用工具.
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Component
public class DbBasedScriptInvokeHandler extends AbstractScriptBasedScriptInvokeHandler
        implements AutoActionHandler {
    private final HandlerConfigImpl handlerConfig = new HandlerConfigImpl();

    @Resource
    private InvokeScriptService invokeScriptService;
    @Resource
    private InvokeLogService invokeLogService;

    @Override
    public String getName() {

        return "invoke-script-db";
    }

    @Override
    public HandlerConfig getConfig() {

        return handlerConfig;
    }

    @Override
    protected InvokeContext buildContext(Object input, String name, Class<?> clazz) {
        Assert.notBlank(name, "Parameter \"name\" must not blank. ");
        Assert.notNull(clazz, "Parameter \"clazz\" must not null. ");
        // 判断是否在 XxlJob 中
        boolean inXxlJob = false;
        if (input != null) {
            Map map = input instanceof Map ? (Map) input : BeanUtils.createBeanMap(input);
            Object inXxlJobObj = map.get("inXxlJob");
            if (inXxlJobObj instanceof Boolean) {
                inXxlJob = (boolean) inXxlJobObj;
            }
            else if (inXxlJobObj instanceof CharSequence) {
                inXxlJob = "true".equalsIgnoreCase(String.valueOf(inXxlJobObj));
            }
        }
        // 构造上下文对象
        InvokeContextImpl context = new InvokeContextImpl(input, name, clazz);
        context.setLog(new SimpleLog(log, inXxlJob));
        return context;
    }

    @Override
    protected void loadConfig(InvokeContext context) {
        String invokeName = context.getInvokeName();
        ScriptInvokeConfig config = invokeScriptService.findByName(invokeName);
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
        ScriptInvokeConfig config = (ScriptInvokeConfig) context.getConfig();
        // 获取缓存配置
        Dict cacheConfig = Dict.of(config.getCacheConfig());
        String cacheName = cacheConfig.getString("cacheName");
        String cacheKey = cacheConfig.getString("cacheKey");
        // 处理缓存Key
        cacheKey = StrUtil.isNotBlank(cacheKey)
                ? (String) eval(config.getEngine(), cacheKey, context) : null;
        // 缓存名称 和 缓存Key 不为空，尝试走缓存
        if (StrUtil.isNotBlank(cacheName) && StrUtil.isNotBlank(cacheKey)) {
            Object rawOutput = CacheUtils.get(cacheName, cacheKey, () -> {
                DbBasedScriptInvokeHandler.super.doInvoke(context);
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
            form.setType(THREE);
            form.setInvokeName(context.getInvokeName());
            form.setTime(new Date());
            form.setExpectedClass(context.getExpectedClass().getName());
            form.setRawInput(JsonUtils.toJsonString(context.getRawInput(), PRETTY_FORMAT));
            form.setConfig(JsonUtils.toJsonString(context.getConfig(), PRETTY_FORMAT));
            form.setRawOutput(JsonUtils.toJsonString(context.getRawOutput(), PRETTY_FORMAT));
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

    /**
     * 让上下文对象含有 slf4j 的日志对象（也许是被包装之后的对象），方便在 脚本中 打印日志.
     * @author Sagiri
     */
    public static class InvokeContextImpl extends AbstractInvokeActionHandler.InvokeContextImpl {
        private Object log;

        public InvokeContextImpl(Object rawInput, String invokeName, Class<?> expectedClass) {
            setExpectedClass(expectedClass);
            setInvokeName(invokeName);
            setRawInput(rawInput);
        }

        public InvokeContextImpl() {

        }

        public Object getLog() {
            return log;
        }

        public void setLog(Object log) {
            this.log = log;
        }
    }

    /**
     * 结合了 XxlJob 日志打印的简单日志对象.
     * @author Sagiri
     */
    public static class SimpleLog {
        private final boolean inXxlJob;
        private final Logger log;

        public SimpleLog(Logger log, boolean inXxlJob) {
            this.inXxlJob = inXxlJob;
            this.log = log;
        }

        public void debug(String format, Object... arguments) {
            log.debug(format, arguments);
            if (inXxlJob) {
                XxlJobLogger.log(format, arguments);
            }
        }

        public void debug(String msg, Throwable th) {
            log.debug(msg, th);
            if (inXxlJob) {
                XxlJobLogger.log(msg);
                XxlJobLogger.log(th);
            }
        }

        public void info(String format, Object... arguments) {
            log.info(format, arguments);
            if (inXxlJob) {
                XxlJobLogger.log(format, arguments);
            }
        }

        public void info(String msg, Throwable th) {
            log.info(msg, th);
            if (inXxlJob) {
                XxlJobLogger.log(msg);
                XxlJobLogger.log(th);
            }
        }

        public void warn(String format, Object... arguments) {
            log.warn(format, arguments);
            if (inXxlJob) {
                XxlJobLogger.log(format, arguments);
            }
        }

        public void warn(String msg, Throwable th) {
            log.warn(msg, th);
            if (inXxlJob) {
                XxlJobLogger.log(msg);
                XxlJobLogger.log(th);
            }
        }

        public void error(String format, Object... arguments) {
            log.error(format, arguments);
            if (inXxlJob) {
                XxlJobLogger.log(format, arguments);
            }
        }

        public void error(String msg, Throwable th) {
            log.error(msg, th);
            if (inXxlJob) {
                XxlJobLogger.log(msg);
                XxlJobLogger.log(th);
            }
        }
    }

}
