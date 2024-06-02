package sagiri.invoke.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.action.support.http.HttpInvokeConfig;
import kunlun.data.Dict;
import kunlun.data.validation.support.ValidationConfig;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeHttpMapper;
import sagiri.invoke.pojo.entity.InvokeHttp;
import sagiri.invoke.service.InvokeConfigService;
import sagiri.invoke.service.InvokeHttpService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static kunlun.common.constant.Numbers.ONE;
import static kunlun.common.constant.Numbers.ZERO;

/**
 * Http调用配置表 服务实现类
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Service
public class InvokeHttpServiceImpl extends ServiceImpl<InvokeHttpMapper, InvokeHttp>
        implements InvokeHttpService {

    @Resource
    private InvokeConfigService invokeConfigService;

    protected HttpInvokeConfig build(InvokeHttp invokeHttp, Dict configs) {
        // 构建对象
        HttpInvokeConfig invokeConfig = new HttpInvokeConfig();
        invokeConfig.setScriptEngine(invokeHttp.getScriptEngine());
        invokeConfig.setCharset(invokeHttp.getCharset());
        invokeConfig.setMethod(invokeHttp.getMethod());
        invokeConfig.setUrl(invokeHttp.getUrl());
        invokeConfig.setHeaders(invokeHttp.getHeaders());
        invokeConfig.setInputType(invokeHttp.getInputType());
        invokeConfig.setParameters(invokeHttp.getParameters());
        invokeConfig.setBody(invokeHttp.getBody());
        invokeConfig.setOutputType(invokeHttp.getOutputType());
        invokeConfig.setOutput(invokeHttp.getOutput());
        // Input validations.
        String inputValidationStr = invokeHttp.getInputValidations();
        if (StrUtil.isNotBlank(inputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(inputValidationStr, ValidationConfig.class);
            invokeConfig.setInputValidations(list);
        }
        // Output validations.
        String outputValidationStr = invokeHttp.getOutputValidations();
        if (StrUtil.isNotBlank(outputValidationStr)) {
            List<ValidationConfig> list = JSON.parseArray(outputValidationStr, ValidationConfig.class);
            invokeConfig.setOutputValidations(list);
        }
        // Cache config.
        String cacheConfigStr = invokeHttp.getCacheConfig();
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
    public HttpInvokeConfig findByName(String name) {
        // 参数校验
        VerifyUtils.notBlank(name, "调用名称不能为空！");
        // 数据库查询
        List<InvokeHttp> list = list(Wrappers.lambdaQuery(InvokeHttp.class)
                .eq(InvokeHttp::getDeleteStatus, ZERO)
                .eq(InvokeHttp::getName, name));
        // 查不到返回 null
        if (CollUtil.isEmpty(list)) { return null; }
        // 查到多条，直接报错
        VerifyUtils.isTrue(list.size() == ONE, "根据调用名称查询到多条配置信息！");
        InvokeHttp invokeHttp = CollUtil.getFirst(list);
        if (invokeHttp == null) { return null; }
        // 必要参数校验
        VerifyUtils.notBlank(invokeHttp.getScriptEngine(), "脚本引擎名称不能为空！");
        VerifyUtils.notNull(invokeHttp.getMethod(), "Http方法未配置或无效！");
        VerifyUtils.notBlank(invokeHttp.getUrl(), "Http的URL地址不能为空！");
        VerifyUtils.notNull(invokeHttp.getInputType(), "输入类型未配置或无效！");
        VerifyUtils.notNull(invokeHttp.getOutputType(), "输出类型未配置或无效！");
        // 根据配置ID查询配置信息
        Long configId = invokeHttp.getConfigId();
        Dict configs = null;
        if (configId != null) {
            configs = invokeConfigService.findById(configId);
        }
        // 转换
        return build(invokeHttp, configs);
    }

}
