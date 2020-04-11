package com.baiiu.workhard.anr

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.baiiu.library.LogUtil
import com.baiiu.workhard.BActivity
import com.baiiu.workhard.R
import com.baiiu.workhard.base.BaseFragment
import com.baiiu.workhard.base.MainThreadExecutor
import kotlinx.android.synthetic.main.fragment_anr.*


/**
 * author: zhuzhe
 * time: 2020-04-10
 * description:
 */
class ANRFragment : BaseFragment() {

    override fun provideLayoutId(): Int {
        return R.layout.fragment_anr
    }

    override fun initOnCreateView() {

        button_toB.setOnClickListener {
            startActivity(Intent(mContext, BActivity::class.java))
            LogUtil.d(BActivity.TAG, "aaaaaaaaaaa")
            Thread.sleep(20_000)
            LogUtil.d(BActivity.TAG, "bbbbbbbbbbb")
        }

        val rotateAnimation = RotateAnimation(0.0F, 360F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.repeatMode = Animation.RESTART
        rotateAnimation.duration = 3000
        iv_animation.startAnimation(rotateAnimation)

        val rotateAnimator = ObjectAnimator
                .ofFloat(iv_animator, "rotation", 0F, 360F)
                .setDuration(3000)
        rotateAnimator.repeatMode = ValueAnimator.RESTART
        rotateAnimator.repeatCount = ValueAnimator.INFINITE
        rotateAnimator.start()

        MainThreadExecutor.postDelay(
                Runnable { mContext.startService(Intent(mContext, ANRService::class.java)) },
                3000
        )

    }

}