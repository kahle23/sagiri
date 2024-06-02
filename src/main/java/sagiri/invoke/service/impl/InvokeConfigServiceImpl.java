package sagiri.invoke.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.data.Dict;
import kunlun.exception.util.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeConfigMapper;
import sagiri.invoke.pojo.entity.InvokeConfig;
import sagiri.invoke.service.InvokeConfigService;

import static kunlun.common.constant.Numbers.ZERO;

/**
 * 调用相关配置表 服务实现类
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Service
public class InvokeConfigServiceImpl extends ServiceImpl<InvokeConfigMapper, InvokeConfig>
        implements InvokeConfigService {

    @Override
    public Dict findById(Long configId) {
        // 参数校验
        VerifyUtils.notNull(configId, "配置ID不能为空！");
        // 数据库查询
        InvokeConfig invokeConfig = getOne(Wrappers.lambdaQuery(InvokeConfig.class)
                .eq(InvokeConfig::getDeleteStatus, ZERO)
                .eq(InvokeConfig::getId, configId));
        // 查不到返回 null
        if (invokeConfig == null) { return null; }
        String content = invokeConfig.getContent();
        if (StrUtil.isBlank(content)) { return null; }
        // 转换并返回
        return Dict.of(JSON.parseObject(content));
    }

}
