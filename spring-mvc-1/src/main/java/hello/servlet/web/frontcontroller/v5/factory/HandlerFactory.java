package hello.servlet.web.frontcontroller.v5.factory;

public class HandlerFactory {
    public HandlerMap makeHandlerMap(HandlerMap handlerMap) {
        if (handlerMap instanceof HandlerMappingMapV5) {
            return new HandlerMappingMapV5();
        } else {
            throw new IllegalArgumentException("Handler Not Found");
        }
    }
}