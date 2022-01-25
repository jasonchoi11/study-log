package me.minikuma.discount;

import me.minikuma.member.Member;

public interface DiscountPolicy {
    /**
     *
     * @param member 회원
     * @param price 가격
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
