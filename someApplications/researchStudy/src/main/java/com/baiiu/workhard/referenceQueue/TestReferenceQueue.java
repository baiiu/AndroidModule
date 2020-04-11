package com.baiiu.workhard.referenceQueue;

import com.baiiu.library.LogUtil;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * author: zhuzhe
 * time: 2020-04-11
 * description:
 */
public class TestReferenceQueue {
    static final String TAG = "GarbageObject";

    private final ReferenceQueue<GarbageObject> queue = new ReferenceQueue<>();
    static GarbageObject sGarbageObject = null;

    public void test() {
//        testWeak();
        testPhantom();


    }


    private static class KeyedWeakReference extends WeakReference<GarbageObject> {
        final String key;

        KeyedWeakReference(GarbageObject referent, String key, ReferenceQueue<GarbageObject> q) {
            super(referent, q);
            this.key = key;
        }
    }


    private void testWeak() {
        KeyedWeakReference reference = new KeyedWeakReference(new GarbageObject(), "keyOne", queue);
        LogUtil.d(TAG, "111111111beforeGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);
        releaseWeakReference(reference);

        sGarbageObject = null;
        LogUtil.d(TAG, "222222222beforeGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);
        releaseWeakReference(reference);
    }

    private void releaseWeakReference(KeyedWeakReference reference) {
        KeyedWeakReference ref;
        while ((ref = (KeyedWeakReference) queue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref + ", " + ref.get() + ", " + ref.key);
        }

        runGC();

        LogUtil.d(TAG, "AfterGC: " + reference.get() + "， " + reference.key + ", " + sGarbageObject);

        while ((ref = (KeyedWeakReference) queue.poll()) != null) {
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
        KeyedPhantomReference reference = new KeyedPhantomReference(new GarbageObject(), "keyOne", queue);
        LogUtil.d(TAG, "111111111beforeGC: " + reference.get() + ", " + reference.key);
        releasePhantomReference(reference);

        sGarbageObject = null;
        LogUtil.d(TAG, "222222222beforeGC: " + reference.get() + ", " + reference.key);
        releasePhantomReference(reference);
    }

    private void releasePhantomReference(KeyedPhantomReference reference) {
        KeyedPhantomReference ref;
        while ((ref = (KeyedPhantomReference) queue.poll()) != null) {
            LogUtil.d(TAG, "beforeGC queue: " + ref + ", " + ref.get() + ", " + ref.key);
        }

        runGC();

        LogUtil.d(TAG, "AfterGC: " + reference.get() + ", " + sGarbageObject);

        while ((ref = (KeyedPhantomReference) queue.poll()) != null) {
            LogUtil.d(TAG, "AfterGC queue: " + ref + ", " + ref.get() + ", " + ref.key + ", " + sGarbageObject);
        }
    }

    private static void runGC() {
        Runtime.getRuntime().gc();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var2) {
            throw new AssertionError();
        }

        System.runFinalization();

    }


}
