package me.minikuma.effectivejava.item01;

public class CoffeeOrder {
    public static void main(String[] args) {
        Coffee order1 = new Coffee("아메리카노", 3);
        Coffee order2 = new Coffee("라떼", 1);

        Coffee coffeeOrder = Coffee.orderCoffee("더블라떼", 2);

        // Cache
        Coffee coffeeOrder1 = Coffee.orderCoffeeCache();
        Coffee coffeeOrder2 = Coffee.orderCoffeeCache();

        // Sub Type
        Coffee starCoffee = Coffee.getCoffeeShop(true);
        Coffee coffee = Coffee.getCoffeeShop(false);

        // 주문서
        System.out.println("order 1 주문 내역 = " + order1.type + ", " + order1.cup);
        System.out.println("order 2 주문 내역 = " + order2.type + ", " + order2.cup);
        System.out.println("coffeeOrder 주문 내역 = " + coffeeOrder.type + ", " + coffeeOrder.cup);
        System.out.println("Cache Instance = " + coffeeOrder1 + ", " + coffeeOrder2);
        System.out.println("Sub-Type Instance = " + starCoffee + ", " + coffee);
    }
}
