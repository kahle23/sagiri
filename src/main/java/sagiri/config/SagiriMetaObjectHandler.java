package sagiri.config;

import kunlun.db.jdbc.support.mybatisplus.AbstractMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 基于 mybatis plus 的数据库字段自动填充处理器.
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Component
public class SagiriMetaObjectHandler extends AbstractMetaObjectHandler {

    @Override
    protected void init() {
        super.init();
        // 此处的 createUser、tenantId、platform 等其实应该从上下文工具获取
        registerConfig("platform", "int0");
        registerConfig("tenantId", "int0");
        registerConfig("createUser", "int0");
        registerConfig("createTime", "nowDate");
        registerConfig("modifyUser", "int0");
        registerConfig("modifyTime", "nowDate");
        registerConfig("deleteStatus", "int0");
    }

}
