package sagiri.invoke.handler;

import kunlun.action.invoke.ai.AbstractScriptBasedAiInvokeAction;
import kunlun.action.invoke.ai.AiInvokeConfig;
import kunlun.action.support.AutoAction;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.handler.ScriptHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sagiri.invoke.service.InvokeAiService;

import javax.annotation.Resource;

/**
 * 基于 数据库配置 的 AI 调用工具.
 * @author Sagiri
 * @since 2024-07-10
 */
@Slf4j
@Component
public class DbBasedAiHandler extends AbstractScriptBasedAiInvokeAction implements AutoAction {

    @Resource
    private InvokeAiService invokeAiService;
    @Resource
    private ScriptHandler scriptHandler;

    @Override
    public String getName() {

        return "invoke-ai-db";
    }

    @Override
    protected ScriptHandler getScriptHandler() {

        return scriptHandler;
    }

    @Override
    protected void loadConfig(InvokeContext context) {
        String invokeName = context.getInvokeName();
        AiInvokeConfig config = invokeAiService.findByName(invokeName);
        VerifyUtils.notNull(config, "根据调用名称无法找到对应的配置，配置无效或未配置！");
        context.setConfig(config);
    }

}
