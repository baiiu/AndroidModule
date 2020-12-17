package com.baiiu.workhard.lock;

import com.baiiu.library.LogUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author: baiiu
 * time: 2020/12/17
 * description:
 */
public class LockTest {

    public static void test() {
//        Thread thread1 = new Thread(new TestRunnable());
//        thread1.start();

        Thread thread2 = new Thread(new TestRunnable());
        thread2.start();

        // 加上sleep代码不会IllegalMonitorStateException
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        thread2.interrupt();
    }


    private static class TestRunnable implements Runnable {

        private static Lock lock = new ReentrantLock();

        @Override
        public void run() {


            try {
                /*
                    lock 先获取锁，无论线程是否interrupt，不会IllegalMonitorStateException

                    lockInterruptibly,线程立马调用interrupt，该代码当还在acquireInterruptibly判断线程状态时就已抛出异常，不会进入tryAcquire去setExclusiveOwnerThread，导致getExclusiveOwnerThread为null；
                    但是在finally语句中去tryrelease，导致IllegalMonitorStateException；
                 */
//                lock.lock();
                lock.lockInterruptibly();

                LogUtil.e(Thread.currentThread().getName() + " running");

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LogUtil.e(Thread.currentThread().getName() + " finished");
            } catch (InterruptedException e) {
                LogUtil.e(Thread.currentThread().getName() + " interrupted");
            } finally {
                lock.unlock();
            }
        }
    }
}
