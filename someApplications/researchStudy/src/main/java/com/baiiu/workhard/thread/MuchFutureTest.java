package com.baiiu.workhard.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author: zhuzhe
 * time: 2018/12/20
 * description:
 */
class MuchFutureTest {

    /*
        future执行的缺点： 一大推扔进线程池中，需要遍历获取结果，
        如果有慢的线程一直在阻塞，则后面先执行完的线程也要被阻塞

        使用ExecutorCompletionService可以避免该缺点，执行完了优先返回

     */
    public static void main(String[] args) {
        System.out.println("主线程 start");
//        testFutures();
        testExecutorCompletionService();
    }

    /*
        使用ExecutorCompletionService，谁快谁先出来
            主线程 start
            子线程执行: 1， pool-1-thread-2
            子线程执行: 0， pool-1-thread-1
            子线程执行: 3， pool-1-thread-4
            子线程执行: 5， pool-1-thread-4
            子线程执行: 7， pool-1-thread-5
            子线程执行: 9， pool-1-thread-6
            主线程获取执行结果: 0, 1
            主线程获取执行结果: 1, 0
            主线程获取执行结果: 2, 3
            主线程获取执行结果: 3, 5
            主线程获取执行结果: 4, 7
            主线程获取执行结果: 5, 9
            子线程执行: 2， pool-1-thread-3
            主线程获取执行结果: 6, 2
            子线程执行: 4， pool-1-thread-2
            主线程获取执行结果: 7, 4
            子线程执行: 6， pool-1-thread-1
            主线程获取执行结果: 8, 6
            子线程执行: 8， pool-1-thread-4
            主线程获取执行结果: 9, 8
     */
    private static void testExecutorCompletionService() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(executor);

        int count = 10;
        for (int i = 0; i < count; ++i) {
            executorCompletionService.submit(new NumberCallable(i));
        }

        try {
            for (int i = 0; i < count; ++i) {
                System.out.println("主线程获取执行结果: " + i + ", " + executorCompletionService.take().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        普通执行，按顺序执行：
            主线程 start
            子线程执行: 0， pool-1-thread-1
            子线程执行: 1， pool-1-thread-2
            子线程执行: 3， pool-1-thread-1
            子线程执行: 5， pool-1-thread-1
            子线程执行: 7， pool-1-thread-1
            子线程执行: 9， pool-1-thread-1
            主线程获取执行结果: 0, 0
            主线程获取执行结果: 1, 1
            子线程执行: 2， pool-1-thread-2
            主线程获取执行结果: 2, 2
            主线程获取执行结果: 3, 3
            子线程执行: 4， pool-1-thread-3
            主线程获取执行结果: 4, 4
            主线程获取执行结果: 5, 5
            子线程执行: 6， pool-1-thread-4
            主线程获取执行结果: 6, 6
            主线程获取执行结果: 7, 7
            子线程执行: 8， pool-1-thread-5
            主线程获取执行结果: 8, 8
            主线程获取执行结果: 9, 9
     */
    private static void testFutures() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<String> submit = executorService.submit(new NumberCallable(i));
            list.add(submit);
        }

        try {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("主线程获取执行结果: " + i + ", " + list.get(i).get());
            }
        } catch (Exception e) {
            // do nothing
        }

        executorService.shutdown();
    }

    private static class NumberCallable implements Callable<String> {
        private final int num;

        NumberCallable(int num) {
            this.num = num;
        }

        @Override
        public String call() throws Exception {
            if (num % 2 == 0) {
                Thread.sleep(num * 1000);
            }

            System.out.println("子线程执行: " + num + "， " + Thread.currentThread().getName());

            return String.valueOf(num);
        }
    }


}
