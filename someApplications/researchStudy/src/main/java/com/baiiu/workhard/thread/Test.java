package com.baiiu.workhard.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: zhuzhe
 * time: 2018/12/20
 * description:
 */
class Test {

    /*
        Future.get()是阻塞方法么，
        子线程如果还没执行完，应该是阻塞了
     */
    public static void main(String[] args) {
//        Future<String> future = testCallable_Future();
        Future<String> future = testCallable_FutureTask();
//        Future<String> future = testCallable_FutureTask_Thread();


//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("主线程执行1");

        try {
            System.out.println("主线程执行2");
            System.out.println(future.get());
            System.out.println("主线程执行3");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有线程执行完毕");
    }

    private static Future<String> testCallable_FutureTask_Thread() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程执行1");

                int sum = 0;
                for (int i = 0; i < 10000; i++) {
                    sum += i;
                }
//                Thread.sleep(2000);

                System.out.println("子线程执行2");

                return String.valueOf(sum);
            }
        });

        new Thread(futureTask).start();

        return futureTask;

    }

private static Future<String> testCallable_FutureTask() {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            System.out.println("子线程执行1");

//                Thread.sleep(2000);

            int sum = 0;
            for (int i = 0; i < 10000; i++) {
                sum += i;
            }

            System.out.println("子线程执行2");
            return String.valueOf(sum);
        }
    });
    Future<?> submit = threadPoolExecutor.submit(futureTask);
//        threadPoolExecutor.shutdown(); // 能触发callable运行

    try {
        System.out.println("submit: " + submit.get());
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }

    return futureTask;
}

    /*
        Callable + Future，使用线程池提交任务

        future.get应该是阻塞方法，会卡UI，待测试
     */
    private static Future<String> testCallable_Future() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();

        Future<String> result = threadPoolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程执行1");

//                Thread.sleep(2000);

                int sum = 0;
                for (int i = 0; i < 10000; i++) {
                    sum += i;
                }

                System.out.println("子线程执行2");

                return String.valueOf(sum);
            }
        });
//        threadPoolExecutor.shutdown(); // 能触发callable运行

        return result;

    }

}
