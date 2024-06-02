package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import kunlun.action.support.http.HttpInvokeConfig;
import sagiri.invoke.pojo.entity.InvokeHttp;

/**
 * Http调用配置表 服务类
 * @author Sagiri
 * @since 2024-06-02
 */
public interface InvokeHttpService extends IService<InvokeHttp> {

    /**
     * 根据调用名称查询对应的调用配置.
     * @param name 待查询的调用名称
     * @return 查询到的 Http 调用配置 或者 Null
     */
    HttpInvokeConfig findByName(String name);

}
