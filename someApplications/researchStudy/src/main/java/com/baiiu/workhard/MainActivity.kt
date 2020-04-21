package com.baiiu.workhard

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.baiiu.library.LogUtil
import com.baiiu.workhard.referenceQueue.TestReferenceQueue
import com.baiiu.workhard.spi.SPITest
import com.baiiu.workhard.touchEvent.TouchEventFragment

/**
 * author: zhuzhe
 * time: 2020-04-09
 * description:
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = getFragment()
        fragment?.let {
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, it, "mainFragemnt")
                    .commit()
        }

        TestReferenceQueue().test()
        SPITest.test()

    }

    private fun getFragment(): Fragment? {
        return null
//        return TouchEventFragment()
//        return ANRFragment()
    }


    override fun onPause() {
        super.onPause()
        LogUtil.d(BActivity.TAG, "MainActivity#onPause")
    }


    var currentX = 0
    var currentY = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            currentX = ev.x.toInt()
            currentY = ev.y.toInt()
            getTouchedView(findViewById(android.R.id.content), currentX, currentY)
        }
        return super.dispatchTouchEvent(ev)
    }


    /*
        遍历，如果当前xy还在view内，并且view还有子view，继续向下遍历

        打印出一条事件分发链条
     */
    private fun getTouchedView(view: View?, x: Int, y: Int) {
        if (view == null) return


        if (isViewUnder(view, x, y)) {
//            LogUtil.e(view)
        }

        if (view is ViewGroup) {
            var i = 0
            val childCount = view.childCount
            while (i < childCount) {
                getTouchedView(view.getChildAt(i), x, y)
                ++i
            }
        }
    }


    fun isViewUnder(view: View?, x: Int, y: Int): Boolean {
        if (view == null) {
            return false
        }
        val arr = IntArray(2)
        view.getLocationInWindow(arr)

        return x >= arr[0]
                && x < arr[0] + view.measuredWidth
                && y >= arr[1]
                && y < arr[1] + view.measuredHeight
    }
}