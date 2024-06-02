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

/**
 * Jdbc调用配置表 前端控制器
 * @author Sagiri
 * @since 2024-06-02
 * @folder @基础功能/Invoke工具/Jdbc调用
 */
@Slf4j
@Controller
public class InvokeJdbcController {

    /**
     * 运行 Jdbc 调用工具.
     * @param data 入参
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping(UrlMapping.INVOKE_JDBC_EXECUTE)
    public Result<Object> execute(@RequestBody Dict data) {
        log.info("Invoke jdbc input: {}", JSON.toJSONString(data));
        String actionName = "invoke-jdbc-db", invokeAttrName = "invokeName";
        String invokeName = data.getString(invokeAttrName);
        VerifyUtils.notBlank(invokeName, "调用名称不能为空！");
        data.remove(invokeAttrName);
        return Result.success(ActionUtils.execute(actionName, data, invokeName, Object.class));
    }

}

