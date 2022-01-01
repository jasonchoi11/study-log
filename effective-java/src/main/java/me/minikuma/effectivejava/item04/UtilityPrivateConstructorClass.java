package me.minikuma.effectivejava.item04;

public class UtilityPrivateConstructorClass {
    private UtilityPrivateConstructorClass() {
        throw new AssertionError();
    }

    static class AnotherClass extends UtilityPrivateConstructorClass {

    }

    public static void print() {
        System.out.println("UtilityPrivateConstructorClass()");
    }

    public static void main(String[] args) {
//        No Create Instance
//        UtilityPrivateConstructorClass up = new UtilityPrivateConstructorClass();
        AnotherClass ac = new AnotherClass();
        UtilityPrivateConstructorClass.print();
    }
}
