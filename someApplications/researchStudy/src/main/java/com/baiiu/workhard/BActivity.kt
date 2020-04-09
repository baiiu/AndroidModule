package com.baiiu.workhard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baiiu.library.LogUtil

/**
 * author: zhuzhe
 * time: 2020-04-09
 * description:
 */
class BActivity : AppCompatActivity(R.layout.activity_b) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG, "BActivity: onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(TAG, "BActivity: onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG, "BActivity: onResume")
    }

    companion object {
        const val TAG = "BActivity";
    }

}