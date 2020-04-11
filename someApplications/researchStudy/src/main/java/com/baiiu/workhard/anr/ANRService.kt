package com.baiiu.workhard.anr

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * author: zhuzhe
 * time: 2020-04-10
 * description:
 */
class ANRService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Thread.sleep(30000)
    }
}