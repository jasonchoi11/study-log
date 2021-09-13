package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Text 전달하기");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String texUnescaped(Model model) {
        model.addAttribute("data", "<b>Text</b> 전달하기");
        return "basic/text-unescaped";
    }
}
