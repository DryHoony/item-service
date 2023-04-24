package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static 사용에 주의
    // 멀티쓰레드 환경에서는 ConcurrentHashMap 사용
    private static long sequence = 0L; // static
    // 마찬가지로 동시에 접근하면 값이 꼬일 수 있다 >> '어토믹 롱?' 등 다른걸 사용


    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        // 안전하게 한번 감싸서 반환 - 그냥 반환하면 store 에 추가로 save 하는 등 값 변화를 줄 수 있다 >> 방지
        // 타입을 바꿔야 하는 문제도 있고
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);

        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){ // test 용
        store.clear();
    }







}
