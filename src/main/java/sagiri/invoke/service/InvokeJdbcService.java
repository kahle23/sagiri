package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import kunlun.action.support.jdbc.JdbcInvokeConfig;
import sagiri.invoke.pojo.entity.InvokeJdbc;

/**
 * Jdbc调用配置表 服务类
 * @author Sagiri
 * @since 2024-06-02
 */
public interface InvokeJdbcService extends IService<InvokeJdbc> {

    /**
     * 根据调用名称查询对应的调用配置.
     * @param name 待查询的调用名称
     * @return 查询到的 Jdbc 调用配置 或者 Null
     */
    JdbcInvokeConfig findByName(String name);

}
