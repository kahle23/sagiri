package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import kunlun.data.Dict;
import sagiri.invoke.pojo.entity.InvokeConfig;

/**
 * 调用相关配置表 服务类
 * @author Sagiri
 * @since 2024-06-02
 */
public interface InvokeConfigService extends IService<InvokeConfig> {

    /**
     * 根据 配置ID 查询配置对象.
     * @param configId 待查询的配置 ID
     * @return 查询到的配置对象或者 Null
     */
    Dict findById(Long configId);

}
