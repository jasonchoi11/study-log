package me.minikuma.order;

import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class Order {
    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    // 최종 계산된 금액
    public int calculatePrice() {
        return itemPrice - discountPrice;
    }
}
