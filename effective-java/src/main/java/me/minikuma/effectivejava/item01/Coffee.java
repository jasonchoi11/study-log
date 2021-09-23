package me.minikuma.effectivejava.item01;

import java.util.Objects;

public class Coffee {
    // Cache Instance
    private final static Coffee CACHE_COFFEE_INSTANCE = new Coffee();

    // instance field
    String type;
    int cup;

    // constructor - default
    public Coffee() {

    }

    // constructor - type parameter
    public Coffee(String type) {
        this.type = type;
    }

    // constructor - cup parameter
    public Coffee(int cup) {
        this.cup = cup;
    }

    // constructor - type, cup parameter
    public Coffee(String type, int cup) {
        this.type = type;
        this.cup = cup;
    }

    // 정적 팩토리 메서드
    public static Coffee orderCoffee(String type, int cup) {
        return new Coffee(type, cup);
    }

    // 정적 팩토리 메서드 (Cache)
    public static Coffee orderCoffeeCache() {
        return CACHE_COFFEE_INSTANCE;
    }

    // 외부 파라미터에 의해 객체 타입이 결정되는 구조로 사용
    public static Coffee getCoffeeShop(boolean flag) {
        return flag ? new StarCoffee() : new Coffee();
    }
}
