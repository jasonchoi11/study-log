package demo.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("hello basic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mapping Get V1");
        return "ok";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping Get V2");
        return "ok";
    }

    /**
     *
     *  PathVariable 사용
     *  변수명 같으면 생략
     *  @PathVariable("userId") -> String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("mapping path userId = {} ", userId);
        return "ok";
    }

    /**
     *
     *   PathVariable 다중 맵핑
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingMultiplePath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mapping multiple path = {}, {}", userId, orderId);
        return "ok";
    }

    /**
     *
     * 파라미터 정보를 추가로 맵핑
     * 파라미터가 없는 경우 Bad request 발생
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mapping param");
        return "ok";
    }

    /**
     * 특정 헤더 정보 맵핑
     * 특정 헤더가 없는 경우 Bad request 발생
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mapping header");
        return "ok";
    }

    /**
     *
     * Content-Type 정보를 맵핑 (Consumes 입장)
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume() {
        log.info("mapping consume");
        return "ok";
    }

    /**
     *
     * Content-Type 정보를 맵핑 (produce 입장 - 내가 만들어 낼 정보)
     * Accept 정보
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduce() {
        log.info("mapping produce");
        return "ok";
    }
}
