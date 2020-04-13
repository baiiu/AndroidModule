package com.baiiu.workhard.referenceQueue;

import com.baiiu.library.LogUtil;

/**
 * author: zhuzhe
 * time: 2020-04-11
 * description:
 */
public class GarbageObject {


    @Override
    protected void finalize() throws Throwable {

        LogUtil.d(TestReferenceQueue.TAG, "GarbageObject: finalize111:" + this.toString());

        super.finalize();

        LogUtil.d(TestReferenceQueue.TAG, "GarbageObject: finalize222:" + this.toString());

        TestReferenceQueue.sGarbageObject = this;
//        TestReferenceQueue.sGarbageObject = null;
    }
}
