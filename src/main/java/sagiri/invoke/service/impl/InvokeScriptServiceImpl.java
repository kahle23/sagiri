package sagiri.invoke.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.action.support.script.ScriptInvokeConfig;
import kunlun.data.Dict;
import kunlun.data.validation.support.ValidationConfig;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeScriptMapper;
import sagiri.invoke.pojo.entity.InvokeScript;
import sagiri.invoke.service.InvokeConfigService;
import sagiri.invoke.service.InvokeScriptService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static kunlun.common.constant.Numbers.ONE;
import static kunlun.common.constant.Numbers.ZERO;

/**
 * 脚本调用配置表 服务实现类
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Service
public class InvokeScriptServiceImpl extends ServiceImpl<InvokeScriptMapper, InvokeScript>
        implements InvokeScriptService {

    @Resource
    private InvokeConfigService invokeConfigService;

    protected ScriptInvokeConfig build(InvokeScript invokeScript, Dict configs) {
        // 构建对象
        ScriptInvokeConfig invokeConfig = new ScriptInvokeConfig();
        invokeConfig.setName(invokeScript.getScriptName());
        invokeConfig.setDescription(invokeScript.getScriptDescription());
        invokeConfig.setEngine(invokeScript.getScriptEngine());
        invokeConfig.setContent(invokeScript.getScriptContent());
        // Input validations.
        String inputValidationStr = invokeScript.getInputValidations();
        if (StrUtil.isNotBlank(inputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(inputValidationStr, ValidationConfig.class);
            invokeConfig.setInputValidations(list);
        }
        // Output validations.
        String outputValidationStr = invokeScript.getOutputValidations();
        if (StrUtil.isNotBlank(outputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(outputValidationStr, ValidationConfig.class);
            invokeConfig.setOutputValidations(list);
        }
        // Cache config.
        String cacheConfigStr = invokeScript.getCacheConfig();
        if (StrUtil.isNotBlank(cacheConfigStr)) {
            Map<String, String> map = JSON.parseObject(cacheConfigStr
                    , TypeUtils.parameterizedOf(Map.class, String.class, String.class));
            invokeConfig.setCacheConfig(map);
        }
        // Other configs.
        if (configs != null) {
            invokeConfig.setOtherConfigs(configs);
        }
        return invokeConfig;
    }

    @Override
    public ScriptInvokeConfig findByName(String name) {
        // 参数校验
        VerifyUtils.notBlank(name, "调用名称不能为空！");
        // 数据库查询
        List<InvokeScript> list = list(Wrappers.lambdaQuery(InvokeScript.class)
                .eq(InvokeScript::getDeleteStatus, ZERO)
                .eq(InvokeScript::getName, name));
        // 查不到返回 null
        if (CollUtil.isEmpty(list)) { return null; }
        // 查到多条，直接报错
        VerifyUtils.isTrue(list.size() == ONE, "根据调用名称查询到多条配置信息！");
        InvokeScript invokeScript = CollUtil.getFirst(list);
        if (invokeScript == null) { return null; }
        // 必要参数校验
        VerifyUtils.notBlank(invokeScript.getScriptName(), "脚本名称不能为空！");
        VerifyUtils.notBlank(invokeScript.getScriptEngine(), "脚本引擎名称不能为空！");
        VerifyUtils.notBlank(invokeScript.getScriptContent(), "脚本内容不能为空！");
        // 根据配置ID查询配置信息
        Long configId = invokeScript.getConfigId();
        Dict configs = null;
        if (configId != null) {
            configs = invokeConfigService.findById(configId);
        }
        // 转换
        return build(invokeScript, configs);
    }

}
