package sagiri.invoke.controller;

import com.alibaba.fastjson.JSON;
import kunlun.action.ActionUtils;
import kunlun.common.Result;
import kunlun.data.Dict;
import kunlun.exception.util.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import sagiri.UrlMapping;
import sagiri.invoke.service.InvokeAiService;

import javax.annotation.Resource;

/**
 * AI调用配置表的前端控制器.
 *
 * @author Sagiri
 * @since 2024-07-10
 * @folder @基础功能/Invoke工具/AI调用
 */
@Slf4j
@Controller
public class InvokeAiController {

    @Resource
    private InvokeAiService invokeAiService;

    /**
     * 运行AI调用工具.
     * @param data 入参
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(UrlMapping.INVOKE_AI_EXECUTE)
    public Result<Object> execute(@RequestBody Dict data) {
        log.info("Invoke AI input: {}", JSON.toJSONString(data));
        String invokeName  = (String) data.remove("invokeName");
        String handlerName = "invoke-ai-db";
        VerifyUtils.notBlank(invokeName, "调用名称不能为空！");
        return Result.success(ActionUtils.execute(handlerName+"."+invokeName, data));
    }

}
