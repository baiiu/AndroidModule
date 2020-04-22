package com.baiiu.workhard.foregroundService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.IntegerRes
import androidx.annotation.MainThread
import com.baiiu.library.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author: zhuzhe
 * time: 2020-04-22
 * description:
 */
class MyService : Service() {

    companion object {
        private const val TAG = "MyService"
        const val channelID: String = "channelID"
        var myService: MyService? = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        LogUtil.e(TAG, "MyService#onBind")
        return null
    }

    override fun onCreate() {
        LogUtil.e(TAG, "MyService#onCreate")
        super.onCreate()
        myService = this
        startForeground()

        EventBus.getDefault().register(this)
    }

    @Subscribe(sticky = true)
    fun onInteger(string: String) {
        LogUtil.e(TAG, "MyService#onInteger#stopForeground: $string")

        /*
            ensure stopService after stopForeground
         */
        stopForeground(true)
//        stopSelf()
        stopService(Intent(applicationContext, MyService::class.java))
    }

    override fun onStart(intent: Intent?, startId: Int) {
        LogUtil.e(TAG, "MyService#onStart")
        super.onStart(intent, startId)
        startForeground()
    }

    override fun onDestroy() {
        LogUtil.e(TAG, "MyService#onDestroy")
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun startForeground() {
        LogUtil.e(TAG, "MyService#startForeground")

        val builder = Notification.Builder(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            builder.setChannelId(channelID)
        }

        startForeground(1, builder.build())
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.getSystemService(NotificationManager::class.java).let {
                val channel = NotificationChannel(channelID, channelID, NotificationManager.IMPORTANCE_LOW)
                it.createNotificationChannel(channel)
            }

        }
    }

}