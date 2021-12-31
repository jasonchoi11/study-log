package me.minikuma.effectivejava.item03;

import java.io.Serializable;

public class ElvisSerializable implements Serializable{
    private static transient final ElvisSerializable INSTANCE = new ElvisSerializable();

    private ElvisSerializable() {

    }

    public static ElvisSerializable getInstance() {
        return INSTANCE;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
