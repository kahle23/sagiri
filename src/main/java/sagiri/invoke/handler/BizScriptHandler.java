package sagiri.invoke.handler;

import kunlun.exception.BusinessException;
import kunlun.util.handler.support.ScriptHandlerImpl;
import org.springframework.stereotype.Component;

@Component
public class BizScriptHandler extends ScriptHandlerImpl {

    @Override
    public void throwValidationException(boolean validate, String message) {
        if (!validate) {
            throw new BusinessException(message);
        }
    }

}
