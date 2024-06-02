package sagiri.invoke.job;

import baibao.extension.xxljob.AbstractXxlJobHandler;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import kunlun.action.ActionUtils;
import kunlun.data.Dict;
import kunlun.exception.util.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 基于脚本调用的 XxlJob 实现类（相当于此 Job 可以配置无限个任务）.
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Component
public class InvokeScriptJob extends AbstractXxlJobHandler {
    private static final String IN_XXL_JOB_NAME = "inXxlJob";
    private static final String JOB_NAME = "InvokeScriptJob";

    @Override
    @XxlJob(value = JOB_NAME)
    public ReturnT<String> execute(String param) {
        try {
            start(JOB_NAME, param);
            // 参数解析
            Dict data = StrUtil.isBlank(param) ? Dict.of() : JSON.parseObject(param, Dict.class);
            // 参数提取
            String actionName = "invoke-script-db", invokeAttrName = "invokeName";
            String invokeName = data.getString(invokeAttrName);
            VerifyUtils.notBlank(invokeName, "调用名称不能为空！");
            data.remove(invokeAttrName);
            // inXxlJob 处理
            if (!data.containsKey(IN_XXL_JOB_NAME)) {
                data.set(IN_XXL_JOB_NAME, true);
            }
            // 脚本调用
            Object execute = ActionUtils.execute(actionName, data, invokeName, Object.class);
            log.info("脚本执行结果为：{}", JSON.toJSONString(execute));
            return ReturnT.SUCCESS;
        }
        catch (Exception e) {
            error(JOB_NAME, param, e);
            return new ReturnT<>(ReturnT.FAIL_CODE, e.getMessage());
        }
        finally { finish(JOB_NAME, param); }
    }

}
