package demo.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * {"username": "hello", "age": 20}
 * Content-Type: application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("message body = {} ", messageBody);
        HelloData readValue = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {} ", readValue.getUsername(), readValue.getAge());
        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("message body = {} ", messageBody);
        HelloData readValue = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {} ", readValue.getUsername(), readValue.getAge());
        return "ok";
    }

    // message converter
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData messageBody) {
        log.info("username = {}, age = {} ", messageBody.getUsername(), messageBody.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData = httpEntity.getBody();
        log.info("username = {}, age = {} ", Objects.requireNonNull(helloData).getUsername(), helloData.getAge());
        return "ok";
    }

    // response 나갈 때 JSON 형태로 변환 후 전송
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData messageBody) {
        log.info("username = {}, age = {} ", messageBody.getUsername(), messageBody.getAge());
        return messageBody;
    }
}
