package me.minikuma.effectivejava.item03;

import java.io.Serializable;

public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {

    }
    // API 외부 노출 메서드
    public static Elvis getInstance() {
        return INSTANCE;
    }
}
