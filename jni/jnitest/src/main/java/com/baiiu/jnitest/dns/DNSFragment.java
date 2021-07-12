package com.baiiu.jnitest.dns;

import android.util.Log;

import com.baiiu.jnitest.base.BaseFragment;

import java.net.InetAddress;

/**
 * author: baiiu
 * time: 2021/7/8
 * description:
 */
public class DNSFragment extends BaseFragment {
    static {
        System.loadLibrary("dns-lib");
    }

    private static final String TAG = "mLogU";

    /*
        ipv4和ipv6是分开缓存的

        getaddrinfo(hostname, NULL, &hints, &ai); 和 getaddrinfo(hostname, portstr, &hints, &ai);
        都是具有缓存效果，即这个portstr不作为缓存key的计算规则
     */
    @Override
    protected void initOnCreateView() {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                nativeTest4_NULL();
                nativeTest4_443();
                nativeTest4_1935();
//                nativeTest6_NULL();
//                nativeTest6_443();
//                nativeTest6_1935();

                nativeTest();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                nativeTest();
//            }
//        }).start();


    }

    private void testV4Andv6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                    目前测试发现，多线程情况下，只要第一个请求返回了，第二个线程就能使用缓存；否则还是会触发请求；
                    另外最多有两个请求，其它的排队等待；

                    从源码看不出这块逻辑，总感觉是bug...，key和cache应该是同一个才对，然后去进入等待；不知为何..
                 */
                for (int i = 0; i < 1; i++) {
//                    test(i);
                    testNative();
                    try {
                        Thread.sleep(50); // 延迟50ms，还是发出两次请求，但第二次耗时减少50ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    testNative();

                    // 同一个线程，两次调两次，不会重复请求，已缓存；
//                    nativeTest();
//                    nativeTest();

                }
            }
        }).start();

//        Handler handler = new Handler();
//
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "300============================");
//                test(300);
//                testNative();
//            }
//        }, 300);
//
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "500============================");
//                test(500);
//                testNative();
//            }
//        }, 500);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "800============================");
//                test(800);
//                testNative();
//            }
//        }, 800);
    }

    private void test(final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long now = System.currentTimeMillis();
                    InetAddress.getAllByName("mtplatform-tx-flv.meituan.net");
                    Log.e(TAG, "_getIP: " + i + ", =====>cost: " + (System.currentTimeMillis() - now));
                } catch (Throwable e) {
                    Log.e(TAG, "_getIP: " + e.toString());
                }
            }
        }).start();
    }

    private void testNative() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                nativeTest();
            }
        }).start();
    }

    private native void nativeTest();

    private native void nativeTest4_NULL();

    private native void nativeTest4_443();

    private native void nativeTest4_1935();

    private native void nativeTest6_NULL();

    private native void nativeTest6_443();

    private native void nativeTest6_1935();

}
