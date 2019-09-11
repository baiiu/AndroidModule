package com.baiiu.commontool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.baiiu.commontool.R;

/**
 * Created by aspsine on 16/2/4.
 * <p>
 * 示例可看:
 * https://github.com/Aspsine/LoadFrameLayout
 */
public class LoadFrameLayout extends FrameLayout {
    @LayoutRes
    private int mEmptyViewLayoutResId;
    @LayoutRes
    private int mErrorViewLayoutResId;
    @LayoutRes
    private int mLoadingViewLayoutResId;

    private View emptyView;
    private View errorView;
    private View loadingView;

    private View contentView;

    public LoadFrameLayout(Context context) {
        this(context, null);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.LoadFrameLayout, defStyleAttr, 0);
        try {
            mEmptyViewLayoutResId = a.getResourceId(R.styleable.LoadFrameLayout_emptyView, R.layout.layout_empty);
            mErrorViewLayoutResId = a.getResourceId(R.styleable.LoadFrameLayout_errorView, R.layout.layout_error);
            mLoadingViewLayoutResId = a.getResourceId(R.styleable.LoadFrameLayout_loadingView, R.layout.layout_loading);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);

        if (mEmptyViewLayoutResId != -1) {
            setEmptyView(mEmptyViewLayoutResId);
        }

        if (mErrorViewLayoutResId != -1) {
            setErrorView(mErrorViewLayoutResId);
        }

        if (mLoadingViewLayoutResId != -1) {
            setLoadingView(mLoadingViewLayoutResId);
        }
    }

    public void setEmptyView(View emptyView) {
        if (this.emptyView != emptyView) {
            if (this.emptyView != null) {
                removeView(this.emptyView);
            }
            this.emptyView = emptyView;
            addView(this.emptyView);
        }
    }

    public void setErrorView(View errorView) {
        if (this.errorView != null) {
            removeView(this.errorView);
        }
        if (this.errorView != errorView) {
            this.errorView = errorView;
            addView(errorView);
            this.errorView.setVisibility(GONE);
        }
    }

    public void setLoadingView(View loadingView) {
        if (this.loadingView != null) {
            removeView(this.loadingView);
        }
        if (this.loadingView != loadingView) {
            this.loadingView = loadingView;
            addView(loadingView);
            this.loadingView.setVisibility(GONE);
        }
    }

    public void setContentView(View contentView) {
        if (this.contentView != null) {
            removeView(this.contentView);
        }
        if (this.contentView != contentView) {
            this.contentView = contentView;
            addView(contentView);
        }
    }

    public void setEmptyView(@LayoutRes int emptyViewResId) {
        View view = LayoutInflater.from(getContext()).inflate(emptyViewResId, this, false);
        setEmptyView(view);
    }

    public void setErrorView(@LayoutRes int errorViewResId) {
        View view = LayoutInflater.from(getContext()).inflate(errorViewResId, this, false);
        setErrorView(view);
    }

    public void setLoadingView(@LayoutRes int loadingViewResId) {
        View view = LayoutInflater.from(getContext()).inflate(loadingViewResId, this, false);
        setLoadingView(view);
    }

    public void setContentView(@LayoutRes int contentViewResId) {
        View view = LayoutInflater.from(getContext()).inflate(contentViewResId, this, false);
        setContentView(view);
    }

    public void showEmptyView() {
        showCurrentView(this.emptyView);
    }

    public void showErrorView() {
        showCurrentView(this.errorView);
    }

    public void showLoadingView() {
        showCurrentView(this.loadingView);
    }

    public void showContentView() {
        showCurrentView(this.contentView);
    }

    private void showCurrentView(View specialView) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            child.setVisibility(child == specialView ? VISIBLE : GONE);
        }
    }
}
