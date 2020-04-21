package com.baiiu.workhard

import android.app.Application
import android.content.Context

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
class MyApplication : Application() {

    companion object {
        lateinit var sContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this
    }

}