package com.vanhan.mobileapps.calculator;

import org.junit.Test;

public class CoroutineTestJava {
    void threadForJava() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("thread running: " + Thread.currentThread().getName());
        });
        thread.start();
        thread.join();
    }

    @Test
    public void testThread() throws InterruptedException {
        threadForJava();
    }
}
