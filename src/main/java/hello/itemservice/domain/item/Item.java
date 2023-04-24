package hello.itemservice.domain.item;

import lombok.Data;

@Data // 핵심 도메인 상황에서는 사용하기 위험하다, 지금은 예제니까 ㄱㄱ
//@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // Integer 를 쓰는 이유는 값이 안들어갈 때도 있다고 가정, Null 가능성
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
