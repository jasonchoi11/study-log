package me.minikuma.effectivejava.item04;

public final class UtilityFinalClass {

    public static void print() {
        System.out.println("final class print()");
    }

    /* 불가능
    static class AnotherClass extends UtilityFinalClass {

    }*/


    public static void main(String[] args) {
//        UtilityFinalClass ifc = new UtilityFinalClass();
        UtilityFinalClass.print();
    }
}
