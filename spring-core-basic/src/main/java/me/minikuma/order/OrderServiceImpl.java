package me.minikuma.order;

import me.minikuma.discount.DiscountPolicy;
import me.minikuma.discount.FixDiscountPolicy;
import me.minikuma.member.Member;
import me.minikuma.member.MemberRepository;
import me.minikuma.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 회원 찾기
        Member findMember = memberRepository.findById(memberId);
        // 할인율
        int discountPrice = discountPolicy.discount(findMember, itemPrice);
        // 주문 생성
        return Order.builder()
                .memberId(memberId)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .discountPrice(discountPrice)
                .build();
    }
}
