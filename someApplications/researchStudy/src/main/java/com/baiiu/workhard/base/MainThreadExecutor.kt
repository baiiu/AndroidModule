package com.baiiu.workhard.base

import android.os.Handler
import android.os.Looper

/**
 * author: zhuzhe
 * time: 2020-04-10
 * description:
 */
class MainThreadExecutor {


    companion object {
        private val mHandler = Handler(Looper.getMainLooper())

        fun post(runnable: Runnable) {
            mHandler.post(runnable)
        }

        fun postDelay(runnable: Runnable, time: Long) {
            mHandler.postDelayed(runnable, time)
        }
    }


}