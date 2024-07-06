package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import kunlun.action.support.ai.AiInvokeConfig;
import sagiri.invoke.pojo.entity.InvokeAi;

/**
 * AI调用配置表的服务类.
 *
 * @author Sagiri
 * @since 2024-07-10
 */
public interface InvokeAiService extends IService<InvokeAi> {

    /**
     * 根据调用名称查询对应的调用配置.
     * @param name 待查询的调用名称
     * @return 查询到的 Jdbc 调用配置 或者 Null
     */
    AiInvokeConfig findByName(String name);

}
