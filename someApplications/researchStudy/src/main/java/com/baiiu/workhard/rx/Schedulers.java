package com.baiiu.workhard.rx;


import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
class Schedulers {


    static Scheduler io() {
        return sub.IO_Schedule;
    }

    static Scheduler newThread() {
        return sub.NEW_THREAD;
    }

    private enum sub implements Scheduler {
        IO_Schedule {
            ExecutorService sIOExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
                private final AtomicInteger poolNumber = new AtomicInteger(1);

                @Override
                public Thread newThread(@NonNull Runnable r) {
                    return new Thread(null, r, "io_thread_" + poolNumber.getAndIncrement());
                }
            });

            @Override
            public void schedule(Runnable run) {
                System.out.println("IO_Schedule#run");
                sIOExecutor.execute(run);
            }
        },
        NEW_THREAD {
            @Override
            public void schedule(Runnable run) {
                new Thread(run).start();
            }
        }
    }

}
