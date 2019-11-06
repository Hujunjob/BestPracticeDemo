package com.hiscene.demo2.lock;

/**
 * Created by junhu on 2019-11-06
 */

/**
 * 两个线程同时访问两个对象的相同的synchronized方法
 *
 * @author JSON
 * @date 2019-08-29
 */
public class SynchronizedScene2 implements Runnable {
    static SynchronizedScene2 ss1 = new SynchronizedScene2();
    static SynchronizedScene2 ss2 = new SynchronizedScene2();
    static int a = 0;

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(ss1);
        t1.setName("t1");
        Thread t2 = new Thread(ss2);
        t2.setName("t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("run over:" + a);
    }

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        System.out.println("开始执行:" + Thread.currentThread().getName());
        for (int i = 0; i < 10000; i++) {
            a++;
        }
        try {
            // 模拟执行内容
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行结束:" + Thread.currentThread().getName());
    }
}
