//package sagiri.invoke.handler;
//
//import baibao.ai.support.azure.AbstractAzureOpenAIHandler;
//import kunlun.ai.support.AutoAIHandler;
//import kunlun.data.Dict;
//import kunlun.data.bean.BeanUtils;
//import kunlun.exception.util.VerifyUtils;
//import kunlun.util.Assert;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * Azure Open AI 工具.
// * @author Sagiri
// * @since 2024-07-10
// */
//@Slf4j
//@Component
//public class AzureOpenAiHandler extends AbstractAzureOpenAIHandler implements AutoAIHandler {
//
//    @Override
//    public String getName() {
//
//        return "azure-ai";
//    }
//
//    @Override
//    protected Config getConfig(Object input, String operation, Class<?> clazz) {
//        Assert.isInstanceOf(Dict.class, input, "Parameter \"input\" must instance of dict. ");
//        Object config = ((Dict) input).get("config");
//        VerifyUtils.notNull(config, "配置不能为空！");
//        return BeanUtils.beanToBean(config, Config.class);
//    }
//
//}
