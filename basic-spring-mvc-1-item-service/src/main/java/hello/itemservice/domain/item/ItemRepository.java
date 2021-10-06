package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    // MAP 형태의 저장 공간으로 가정함
    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private final AtomicLong atomicLong = new AtomicLong();

    public Item save(Item item) {
        item.setId(atomicLong.incrementAndGet());
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // TODO: ID 를 사용하지 않음 -> DTO 고려 (명확성)
    public void update(Long updateId, Item updateParam) {
        Item findItem = findById(updateId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        findItem.setOpen(updateParam.isOpen());
        findItem.setRegions(updateParam.getRegions());
        findItem.setItemType(updateParam.getItemType());
        findItem.setDeliveryCode(updateParam.getDeliveryCode());
    }

    // 테스트를 위한 메서드
    public void clearStore() {
        store.clear();
    }
}