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
        super.finalize();
        LogUtil.d(TestReferenceQueue.TAG, "GarbageObject: finalize:" + this.toString());

//        TestReferenceQueue.sGarbageObject = this;
    }
}
