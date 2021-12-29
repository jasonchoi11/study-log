package me.minikuma.effectivejava.item03;

import org.springframework.scheduling.config.Task;

public class SingletonTest implements Runnable {

    public static void main(String[] args) {
        Runnable task = new SingletonTest();
        Thread subTread1 = new Thread(task);
        Thread subTread2 = new Thread(task);
        Thread subTread3 = new Thread(task);
        subTread1.start();
        subTread2.start();
        subTread3.start();
    }

    @Override
    public void run() {
        Singleton singleton = Singleton.getInstance();
        System.out.println(Thread.currentThread() + " singleton = " + singleton);
    }
}
