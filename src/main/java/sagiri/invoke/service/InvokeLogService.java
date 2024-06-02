package sagiri.invoke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sagiri.invoke.pojo.entity.InvokeLog;
import sagiri.invoke.pojo.form.InvokeLogAddForm;

/**
 * 调用日志相关表 服务类
 * @author Sagiri
 * @since 2024-06-02
 */
public interface InvokeLogService extends IService<InvokeLog> {

    /**
     * 增加调用日志.
     * @param form 待增加的调用日志
     */
    void add(InvokeLogAddForm form);

}
