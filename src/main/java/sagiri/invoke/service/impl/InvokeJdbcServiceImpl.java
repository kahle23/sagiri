package sagiri.invoke.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.action.support.jdbc.JdbcInvokeConfig;
import kunlun.data.Dict;
import kunlun.data.validation.support.ValidationConfig;
import kunlun.exception.util.VerifyUtils;
import kunlun.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeJdbcMapper;
import sagiri.invoke.pojo.entity.InvokeJdbc;
import sagiri.invoke.service.InvokeConfigService;
import sagiri.invoke.service.InvokeJdbcService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static kunlun.common.constant.Numbers.ONE;
import static kunlun.common.constant.Numbers.ZERO;

/**
 * Jdbc调用配置表 服务实现类
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Service
public class InvokeJdbcServiceImpl extends ServiceImpl<InvokeJdbcMapper, InvokeJdbc>
        implements InvokeJdbcService {

    @Resource
    private InvokeConfigService invokeConfigService;

    protected JdbcInvokeConfig build(InvokeJdbc invokeHttp, Dict configs) {
        // 构建对象
        JdbcInvokeConfig invokeConfig = new JdbcInvokeConfig();
        invokeConfig.setScriptEngine(invokeHttp.getScriptEngine());
        invokeConfig.setRendererName(invokeHttp.getRendererName());
        invokeConfig.setInput(invokeHttp.getInput());
        invokeConfig.setExecuteType(invokeHttp.getExecuteType());
        invokeConfig.setSql(invokeHttp.getSql());
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
    public JdbcInvokeConfig findByName(String name) {
        // 参数校验
        VerifyUtils.notBlank(name, "调用名称不能为空！");
        // 数据库查询
        List<InvokeJdbc> list = list(Wrappers.lambdaQuery(InvokeJdbc.class)
                .eq(InvokeJdbc::getDeleteStatus, ZERO)
                .eq(InvokeJdbc::getName, name));
        // 查不到返回 null
        if (CollUtil.isEmpty(list)) { return null; }
        // 查到多条，直接报错
        VerifyUtils.isTrue(list.size() == ONE, "根据调用名称查询到多条配置信息！");
        InvokeJdbc invokeJdbc = CollUtil.getFirst(list);
        if (invokeJdbc == null) { return null; }
        // 必要参数校验
        VerifyUtils.notBlank(invokeJdbc.getScriptEngine(), "脚本引擎名称不能为空！");
        VerifyUtils.notNull(invokeJdbc.getExecuteType(), "执行类型不能为空！");
        VerifyUtils.notBlank(invokeJdbc.getSql(), "要执行的SQL不能为空！");
        // 根据配置ID查询配置信息
        Long configId = invokeJdbc.getConfigId();
        Dict configs = null;
        if (configId != null) {
            configs = invokeConfigService.findById(configId);
        }
        // 转换
        return build(invokeJdbc, configs);
    }

}
