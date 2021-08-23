package hello.servlet.web.frontcontroller.v5.factory;

import java.util.Map;

public abstract class HandlerPathFactory {

    public Map<String, Object> makeHandlerPath() {
        return createHandlerPath();
    }

    abstract Map<String, Object> createHandlerPath();
}
