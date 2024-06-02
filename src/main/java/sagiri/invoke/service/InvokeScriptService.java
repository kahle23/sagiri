package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import kunlun.action.support.script.ScriptInvokeConfig;
import sagiri.invoke.pojo.entity.InvokeScript;

/**
 * 脚本调用配置表 服务类
 * @author Sagiri
 * @since 2024-06-02
 */
public interface InvokeScriptService extends IService<InvokeScript> {

    /**
     * 根据调用名称查询对应的调用配置.
     * @param name 待查询的调用名称
     * @return 查询到的 脚本调用配置 或者 Null
     */
    ScriptInvokeConfig findByName(String name);

}
