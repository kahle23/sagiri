package sagiri.invoke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kunlun.data.bean.BeanUtils;
import kunlun.data.validation.support.javax.ValidationUtils;
import kunlun.data.validation.support.javax.group.Create;
import kunlun.exception.util.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sagiri.invoke.mapper.InvokeLogMapper;
import sagiri.invoke.pojo.entity.InvokeLog;
import sagiri.invoke.pojo.form.InvokeLogAddForm;
import sagiri.invoke.service.InvokeLogService;

/**
 * 调用日志相关表 服务实现类
 * @author Sagiri
 * @since 2024-06-02
 */
@Slf4j
@Service
public class InvokeLogServiceImpl extends ServiceImpl<InvokeLogMapper, InvokeLog>
        implements InvokeLogService {

    @Override
    public void add(InvokeLogAddForm form) {
        // 参数校验
        VerifyUtils.notNull(form, "参数不能为空！");
        ValidationUtils.validate(form, Create.class);
        // 参数转换
        InvokeLog invokeLog =
                BeanUtils.beanToBean(form, InvokeLog.class);
        // 保存
        VerifyUtils.isTrue(save(invokeLog), "保存记录失败！");
    }

}
