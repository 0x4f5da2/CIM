package com.chenzhicheng.cim.test;

public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("?????"));
        thread.start();
        thread.interrupt();
    }
}
