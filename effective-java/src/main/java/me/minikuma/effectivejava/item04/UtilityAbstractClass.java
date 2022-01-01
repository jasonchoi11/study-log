package me.minikuma.effectivejava.item04;

public abstract class UtilityAbstractClass {
    public static void print() {
        System.out.println("abstract class print()");
    }

    static class AnotherClass extends UtilityAbstractClass {

    }

    public static void main(String[] args) {
        // UtilityAbstractClass uac = new UtilityAbstractClass(); No Create Instance
        AnotherClass ac = new AnotherClass();
        UtilityAbstractClass.print();
    }
}
