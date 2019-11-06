package com.hiscene.demo2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    static int a = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            if (Thread.currentThread().getName().equals("t1")) {
//                method();
//            } else {
//                method2();
//            }
            method();
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
//            if (Thread.currentThread().getName().equals("t1")) {
//                method();
//            } else {
//                method2();
//            }
            method();
        }
    };

    @Test
    public void testLock() {
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable2);
        t2.setName("t2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }

    private synchronized void method() {
        System.out.println("开始执行:" + Thread.currentThread().getName());
        for (int i = 0; i < 10000; i++) {
//                    System.out.println(a++);
            a++;
        }
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (Thread.currentThread().getName().equals("t1"))
//        throw new RuntimeException("失败");
        System.out.println("执行结束:" + Thread.currentThread().getName());
    }

    private synchronized void method2() {
        System.out.println("开始执行:" + Thread.currentThread().getName());
        for (int i = 0; i < 10000; i++) {
//                    System.out.println(a++);
            a++;
        }
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行结束:" + Thread.currentThread().getName());
    }
}