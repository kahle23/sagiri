package sagiri.acommon.config;

import kunlun.renderer.RenderUtil;
import kunlun.renderer.support.VelocityTextRenderer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class AutoConfig implements InitializingBean {

    @Resource
    private ApplicationContext appContext;

    @Override
    public void afterPropertiesSet() throws Exception {

        RenderUtil.registerRenderer("velocity", new VelocityTextRenderer());
    }

}
