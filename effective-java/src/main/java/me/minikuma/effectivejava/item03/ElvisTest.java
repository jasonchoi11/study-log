package me.minikuma.effectivejava.item03;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class ElvisTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // reflection object 생성 가능
        Class<Elvis> aClass = Elvis.class;
        Constructor<Elvis> declaredConstructor = aClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Elvis newInstance = declaredConstructor.newInstance();
        System.out.println("newInstance = " + newInstance);

        // supplier<T> interface 로 사용 가능
        Supplier<Elvis> supplier1 = Elvis::getInstance; // 메서드 레퍼런스
        Supplier<Elvis> supplier2 = Elvis::getInstance; // 메서드 레퍼런스
        System.out.println("supplier1 = " + supplier1.get());
        System.out.println("supplier2 = " + supplier2.get());
    }
}
