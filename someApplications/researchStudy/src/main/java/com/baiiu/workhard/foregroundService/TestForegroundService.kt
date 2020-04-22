package com.baiiu.workhard.foregroundService

import android.content.Context
import android.content.Intent
import android.os.Build
import com.baiiu.library.LogUtil
import com.baiiu.workhard.base.MainThreadExecutor
import org.greenrobot.eventbus.EventBus

/**
 * author: zhuzhe
 * time: 2020-04-22
 * description:
 */
class TestForegroundService {

    companion object {

        fun start(context: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(Intent(context, MyService::class.java))

                /*
                    startService立刻调用stop，看源码，需要确保先stopForeground，再stopService
                 */
                EventBus.getDefault().postSticky("3")
//                context.stopService(Intent(context, MyService::class.java))
            }

            MainThreadExecutor.postDelay(
                    Runnable {
                        EventBus.getDefault().post("1")
                        EventBus.getDefault().post("2")
//                        context.stopService(Intent(context, MyService::class.java))
                    },
                    3000)
        }
    }


}