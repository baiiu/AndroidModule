package com.baiiu.commontool.mvp;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MVPPresenter<V extends MvpView> {

    void start();

    void attachView(V mvpView);

    void detachView();
}
