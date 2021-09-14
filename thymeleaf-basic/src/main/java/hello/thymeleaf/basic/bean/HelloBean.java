package hello.thymeleaf.basic.bean;

import org.springframework.stereotype.Component;

@Component
public class HelloBean {
    public String hello(String data) {
        return "Hello " + data;
    }
}
