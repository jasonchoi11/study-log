package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> findItems = itemRepository.findAll();
        model.addAttribute("items", findItems);
        return "basic/items";
    }

    /**
     * 전체조회(find all) 검증을 위한 데이터 적재
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 1000, 2));
        itemRepository.save(new Item("itemB", 10000, 20));
    }
}