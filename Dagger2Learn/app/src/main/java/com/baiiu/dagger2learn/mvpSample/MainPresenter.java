package com.baiiu.dagger2learn.mvpSample;

import com.baiiu.dagger2learn.bean.OneApple;
import com.baiiu.dagger2learn.bean.OnePerson;
import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/13 11:36
 * description:
 */
public class MainPresenter {
    private IView mView;

    private OneApple mOneApple;
    private OnePerson mOnePerson;

    @Inject public MainPresenter(OneApple oneApple, OnePerson onePerson) {
        this.mOneApple = oneApple;
        this.mOnePerson = onePerson;
    }


    public void attachView(IView iView) {
        this.mView = iView;
    }

    public void start() {
        mView.showPerson(mOneApple.toString() + mOnePerson.toString());
    }

}
