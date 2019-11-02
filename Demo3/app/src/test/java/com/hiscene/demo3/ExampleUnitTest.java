package com.hiscene.demo3;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testThread() {
        FutureTask<String> tast = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("送货开始");
                Thread.sleep(2000);
                System.out.println("送货结束");
                return "食物";
            }
        });
//        HashMap<String ,String> map = new HashMap<>();
//        Set<HashMap.Entry<String,String>> entry = map.entrySet();
//        Map.Entry<String ,String> s = entry.iterator().next();
//
//        for (HashMap.Entry<String, String> stringStringEntry : entry) {
//            stringStringEntry.getKey()
//        }
        System.out.println("开始去买菜");
        new Thread(tast).start();
        System.out.println("开始去购物");
        try {
            Thread.sleep(1000);

            System.out.println("购物完成，看买菜是否买到了");

            if (tast.isDone()) {
                System.out.println("购物完成，菜也买到了");
            } else {
                System.out.println("购物完成，但是菜还没有买到，等待...");
            }
            String s = tast.get();
            System.out.println("菜买到了：" + s);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              RejectedExecutionHandler handler
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(512), new ThreadPoolExecutor.DiscardOldestPolicy());
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;

        Future<String> future = threadPoolExecutor.submit(new Callable<String >() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        future.get();
        CompletionService<String> service = new ExecutorCompletionService<String>(threadPoolExecutor);
        service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        service.take().get()

    }
}