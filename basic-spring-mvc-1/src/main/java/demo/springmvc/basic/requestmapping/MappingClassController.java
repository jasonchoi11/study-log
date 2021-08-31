package demo.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    // 회원 전체 조회
    @GetMapping("/")
    public String user() {
        return "get users";
    }

    // 회원 등록
    @PostMapping("/")
    public String addUser() {
        return "post users";
    }

    // 회원 단건 조회
    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "get userId = " + userId;
    }

    // 회원 수정
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String userId) {
        return "update userId = " + userId;
    }

    // 회원 삭제
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return "delete userId = " + userId;
    }
}
