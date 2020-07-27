package com.baiiu.workhard.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * author: zhuzhe
 * time: 2018/12/20
 * description:
 */
class ExecutorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("sss");
            }
        });
//        threadPoolExecutor.shutdown();

//        Executors.newSingleThreadExecutor();

//        System.out.println(-1 << (Integer.SIZE - 3));
//        System.out.println(Integer.toBinaryString(-1 << 1));
//        System.out.println(-1 << 1);
//
//        System.out.println(Integer.toBinaryString(-1));
//        System.out.println(Integer.toBinaryString(-1).length());
//        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE >> 1));

    }
}
