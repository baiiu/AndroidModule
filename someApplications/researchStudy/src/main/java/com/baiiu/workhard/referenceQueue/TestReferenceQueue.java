package com.baiiu.workhard.referenceQueue;

import com.baiiu.library.LogUtil;
import com.baiiu.workhard.base.MainThreadExecutor;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * author: zhuzhe
 * time: 2020-04-11
 * description:
 */
public class TestReferenceQueue {

    static final String TAG = "GarbageObject";

    private final ReferenceQueue<GarbageObject> weakQueue = new ReferenceQueue<>();
    private final ReferenceQueue<GarbageObject> phantomQueue = new ReferenceQueue<>();
    static GarbageObject sGarbageObject = null;

    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Reference<? extends GarbageObject> ref = weakQueue.remove();

                        LogUtil.d(TAG, "on weak queue: " + ref + ", " + ref.get() + ", " + sGarbageObject);

                    } catch (InterruptedException e) {
                        LogUtil.d(TAG, e.toString());
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Reference<? extends GarbageObject> ref = phantomQueue.remove();

                        LogUtil.d(TAG, "on phantom queue: " + ref + ", " + ref.get() + ", " + sGarbageObject);

                    } catch (InterruptedException e) {
                        LogUtil.d(TAG, e.toString());
                    }
                }
            }
        }).start();


        testBoth();
//        testWeak();
//        testPhantom();
    }


    private void testBoth() {
//        GarbageObject garbageObject = new GarbageObject();
        sGarbageObject = new GarbageObject();
        KeyedWeakReference key1 = new KeyedWeakReference(new GarbageObject(), "keyOne", weakQueue);
        KeyedPhantomReference key2 = new KeyedPhantomReference(sGarbageObject, "keyTwo", phantomQueue);

        releaseBoth(key1, key2);

//        garbageObject = null;
        sGarbageObject = null;


        releaseBoth(key1, key2);
    }

    private void releaseBoth(KeyedWeakReference key1, KeyedPhantomReference key2) {
        LogUtil.d(TAG, "weak before GC: " + key1.get() + "， " + key1.key + ", " + sGarbageObject);
        LogUtil.d(TAG, "phantom before GC: " + key2.get() + "， " + key2.key + ", " + sGarbageObject);

        KeyedWeakReference ref1;
        while ((ref1 = (KeyedWeakReference) weakQueue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref1 + ", " + ref1.get() + ", " + ref1.key + ", " + sGarbageObject);
        }
        KeyedPhantomReference ref2;
        while ((ref2 = (KeyedPhantomReference) phantomQueue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref2 + ", " + ref2.get() + ", " + ref2.key + ", " + sGarbageObject);
        }

        runGC();

        LogUtil.d(TAG, "weak after GC: " + key1.get() + "， " + key1.key + ", " + sGarbageObject);
        LogUtil.d(TAG, "phantom after GC: " + key2.get() + "， " + key2.key + ", " + sGarbageObject);

        while ((ref1 = (KeyedWeakReference) weakQueue.poll()) != null) {
            LogUtil.d(TAG, "after weak queue: " + ref1 + ", " + ref1.get() + ", " + ref1.key + ", " + sGarbageObject);
        }
        while ((ref2 = (KeyedPhantomReference) phantomQueue.poll()) != null) {
            LogUtil.d(TAG, "after phantom queue: " + ref2 + ", " + ref2.get() + ", " + ref2.key + ", " + sGarbageObject);
        }
    }


    private static class KeyedWeakReference extends WeakReference<GarbageObject> {
        final String key;

        KeyedWeakReference(GarbageObject referent, String key, ReferenceQueue<GarbageObject> q) {
            super(referent, q);
            this.key = key;
        }
    }


    private void testWeak() {
        KeyedWeakReference reference = new KeyedWeakReference(new GarbageObject(), "keyOne", weakQueue);
        LogUtil.d(TAG, "111111111beforeGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);
        releaseWeakReference(reference);

        sGarbageObject = null;
        LogUtil.d(TAG, "222222222beforeGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);
        releaseWeakReference(reference);
    }

    private void releaseWeakReference(KeyedWeakReference reference) {
        KeyedWeakReference ref;
        while ((ref = (KeyedWeakReference) weakQueue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref + ", " + ref.get() + ", " + ref.key);
        }

        runGC();

        LogUtil.d(TAG, "AfterGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);

        while ((ref = (KeyedWeakReference) weakQueue.poll()) != null) {
            LogUtil.d(TAG, "AfterGC queue: " + ref + ", " + ref.get() + ", " + ref.key + ", " + sGarbageObject);
        }
    }


    private static class KeyedPhantomReference extends PhantomReference<GarbageObject> {
        final String key;

        KeyedPhantomReference(GarbageObject referent, String key, ReferenceQueue<? super GarbageObject> q) {
            super(referent, q);
            this.key = key;
        }
    }


    private void testPhantom() {
        KeyedPhantomReference reference = new KeyedPhantomReference(new GarbageObject(), "keyOne", phantomQueue);
        LogUtil.d(TAG, "111111111beforeGC: " + reference.get() + ", " + reference.key);
        releasePhantomReference(reference);

        sGarbageObject = null;
        LogUtil.d(TAG, "222222222beforeGC: " + reference.get() + ", " + reference.key);
        releasePhantomReference(reference);
    }

    private void releasePhantomReference(KeyedPhantomReference reference) {
        KeyedPhantomReference ref;
        while ((ref = (KeyedPhantomReference) phantomQueue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref + ", " + ref.get() + ", " + ref.key);
        }

        runGC();

        LogUtil.d(TAG, "AfterGC: " + reference.get() + ", " + sGarbageObject);

        while ((ref = (KeyedPhantomReference) phantomQueue.poll()) != null) {
            LogUtil.d(TAG, "AfterGC queue: " + ref + ", " + ref.get() + ", " + ref.key + ", " + sGarbageObject);
        }
    }

    private static void runGC() {
        Runtime.getRuntime().gc();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException var2) {
            throw new AssertionError();
        }

        System.runFinalization();

        MainThreadExecutor.Companion.postDelay(new Runnable() {
            @Override
            public void run() {
                runGC();
            }
        }, 10_000);


    }


}
