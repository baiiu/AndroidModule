package com.baiiu.myapplication.module.ultraPtr.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import com.baiiu.myapplication.R;

import butterknife.ButterKnife;

/**
 * Created by aspsine on 16/2/4.
 * <p>
 * 示例可看:
 * https://github.com/Aspsine/LoadFrameLayout
 */
public class LoadFrameLayout extends FrameLayout {
    public static final int LOADING = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    public static final int CONTENT = 3;
    public static final int NO_GONE = 4;
    private OnClickListener mErrorClickListener;


    @IntDef({LOADING, ERROR, EMPTY, CONTENT, NO_GONE})
    @interface LoadFrameState {
    }


    @LayoutRes private int mEmptyViewLayoutResId;
    @LayoutRes private int mErrorViewLayoutResId;
    @LayoutRes private int mLoadingViewLayoutResId;

    private View emptyView; // viewStub --> View
    private View errorView; // viewStub --> View
    private View loadingView;
    private View contentView;

    @LoadFrameState private int mCurrentState = -1;

    public LoadFrameLayout(Context context) {
        this(context, null);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadFrameLayout, defStyleAttr, 0);
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

    public void setEmptyView(@LayoutRes int emptyViewResId) {
        ViewStub viewStub = new ViewStub(getContext());
        viewStub.setLayoutResource(emptyViewResId);
        setEmptyView(viewStub);
    }

    public void setErrorView(@LayoutRes int errorViewResId) {
        ViewStub viewStub = new ViewStub(getContext());
        viewStub.setLayoutResource(errorViewResId);
        setErrorView(viewStub);
    }

    public void setLoadingView(@LayoutRes int loadingViewResId) {
        View view = LayoutInflater.from(getContext())
                .inflate(loadingViewResId, this, false);
        setLoadingView(view);
    }

    public void setContentView(@LayoutRes int contentViewResId) {
        View view = LayoutInflater.from(getContext())
                .inflate(contentViewResId, this, false);
        setContentView(view);
    }

    public void setEmptyView(View emptyView) {
        if (this.emptyView != emptyView) {
            if (this.emptyView != null) {
                removeView(this.emptyView);
            }
            this.emptyView = emptyView;
            addView(this.emptyView);

            emptyView.setVisibility(GONE);
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

    public void setEmptyText(String emptyText) {
        if (emptyView == null || TextUtils.isEmpty(emptyText)) {
            return;
        }
        TextView textView = errorView.findViewById(R.id.tv_empty);
        if (textView != null) {
            textView.setText(emptyText);
        }
    }

    public void setErrorText(String errorText) {
        if (errorView == null || TextUtils.isEmpty(errorText)) {
            return;
        }

        TextView textView = errorView.findViewById(R.id.tv_error);
        if (textView != null) {
            textView.setText(errorText);
        }
    }

    public void setOnErrorClickListener(OnClickListener listener) {
        if (errorView == null || listener == null) {
            return;
        }

        if (errorView instanceof ViewStub) {
            this.mErrorClickListener = listener;
            return;
        }

        mErrorClickListener = null;

        TextView textView = errorView.findViewById(R.id.tv_retry);
        if (textView != null) {
            textView.setOnClickListener(listener);
        }
    }

    public void bind(@LoadFrameState int data) {
        if (mCurrentState == data) {
            return;
        }

        mCurrentState = data;

        if (emptyView != null) {
            if (data == EMPTY) {
                if (emptyView instanceof ViewStub) {
                    emptyView = ((ViewStub) emptyView).inflate();
                }
                emptyView.setVisibility(VISIBLE);
            } else {
                emptyView.setVisibility(GONE);
            }
        }

        if (errorView != null) {
            if (data == ERROR) {
                if (errorView instanceof ViewStub) {
                    errorView = ((ViewStub) errorView).inflate();
                    setOnErrorClickListener(mErrorClickListener);
                }
                errorView.setVisibility(VISIBLE);
            } else {
                errorView.setVisibility(GONE);
            }
        }


        if (loadingView != null) loadingView.setVisibility(data == LOADING ? VISIBLE : GONE);
        if (contentView != null) contentView.setVisibility(data == CONTENT ? VISIBLE : GONE);

        if (NO_GONE == data) {
            for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
                View child = getChildAt(i);
                child.setVisibility(GONE);
            }
        }
    }

}
