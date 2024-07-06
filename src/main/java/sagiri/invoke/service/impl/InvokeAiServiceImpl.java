package sagiri.invoke.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.action.invoke.ai.AiInvokeConfig;
import kunlun.data.Dict;
import kunlun.data.validation.support.ValidationConfig;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeAiMapper;
import sagiri.invoke.pojo.entity.InvokeAi;
import sagiri.invoke.service.InvokeAiService;
import sagiri.invoke.service.InvokeConfigService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static kunlun.common.constant.Numbers.ONE;
import static kunlun.common.constant.Numbers.ZERO;

/**
 * AI调用配置表的服务实现类.
 *
 * @author Sagiri
 * @since 2024-07-10
 */
@Slf4j
@Service
public class InvokeAiServiceImpl extends ServiceImpl<InvokeAiMapper, InvokeAi>
        implements InvokeAiService {

    @Resource
    private InvokeConfigService invokeConfigService;

    protected AiInvokeConfig build(InvokeAi invokeAi, Dict configs) {
        // 构建对象
        AiInvokeConfig aiConfig = new AiInvokeConfig();
        aiConfig.setScriptEngine(invokeAi.getScriptEngine());
        aiConfig.setRendererName(invokeAi.getRendererName());
        aiConfig.setInputConversionScript(invokeAi.getInputConversionScript());
        aiConfig.setSystemPrompt(invokeAi.getSystemPrompt());
        aiConfig.setUserPrompt(invokeAi.getUserPrompt());
        aiConfig.setToolPrompt(invokeAi.getToolPrompt());
        aiConfig.setHandlerName(invokeAi.getHandlerName());
        aiConfig.setMethodName(invokeAi.getMethodName());
        aiConfig.setOutputConversionScript(invokeAi.getOutputConversionScript());
        // Input validations.
        String inputValidationStr = invokeAi.getInputValidations();
        if (StrUtil.isNotBlank(inputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(inputValidationStr, ValidationConfig.class);
            aiConfig.setInputValidations(list);
        }
        // Output validations.
        String outputValidationStr = invokeAi.getOutputValidations();
        if (StrUtil.isNotBlank(outputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(outputValidationStr, ValidationConfig.class);
            aiConfig.setOutputValidations(list);
        }
        // Cache config.
        String cacheConfigStr = invokeAi.getCacheConfig();
        if (StrUtil.isNotBlank(cacheConfigStr)) {
            Map<String, String> map = JSON.parseObject(cacheConfigStr
                    , TypeUtils.parameterizedOf(Map.class, String.class, String.class));
            aiConfig.setCacheConfig(map);
        }
        // Other configs.
        if (configs != null) {
            aiConfig.setOtherConfigs(configs);
        }
        return aiConfig;
    }

    @Override
    public AiInvokeConfig findByName(String name) {
        // 参数校验
        VerifyUtils.notBlank(name, "调用名称不能为空！");
        // 数据库查询
        List<InvokeAi> list = list(Wrappers.lambdaQuery(InvokeAi.class)
                .eq(InvokeAi::getDeleteStatus, ZERO)
                .eq(InvokeAi::getName, name));
        // 查不到返回 null
        if (CollUtil.isEmpty(list)) { return null; }
        // 查到多条，直接报错
        VerifyUtils.isTrue(list.size() == ONE, "根据调用名称查询到多条配置信息！");
        InvokeAi invokeAi = CollUtil.getFirst(list);
        if (invokeAi == null) { return null; }
        // 必要参数校验
        VerifyUtils.notBlank(invokeAi.getScriptEngine(), "脚本引擎名称不能为空！");
        VerifyUtils.notNull(invokeAi.getHandlerName(), "AI处理器的名称不能为空！");
        VerifyUtils.notBlank(invokeAi.getMethodName(), "AI处理器方法名不能为空！");
        // 根据配置ID查询配置信息
        Long configId = invokeAi.getConfigId();
        Dict configs = null;
        if (configId != null) {
            configs = invokeConfigService.findById(configId);
        }
        // 转换
        return build(invokeAi, configs);
    }

}
