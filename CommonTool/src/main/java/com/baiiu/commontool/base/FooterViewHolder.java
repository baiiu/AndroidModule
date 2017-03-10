package com.baiiu.commontool.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.baiiu.commontool.R;
import com.baiiu.commontool.app.UIUtil;

/**
 * Created by baiiu on 2015/11/21.
 * ListView的脚布局，提供三种状态的设置
 */
public class FooterViewHolder extends BaseViewHolder<Integer> {
    public static final int HAS_MORE = 0;
    public static final int NO_MORE = 1;
    public static final int ERROR = 2;
    public static final int GONE = 3;

    private int mCurrentState = -1;


    @BindView(R.id.rl_loading)
    LinearLayout loadingView;

    @BindView(R.id.rl_error)
    LinearLayout errorView;

    @BindView(R.id.rl_no_more)
    LinearLayout noMoreView;

    @BindView(R.id.fl_container)
    FrameLayout mRootView;

    @BindView(R.id.tv_no_more)
    TextView tv_no_more;


    public void setNoMoreText(String text) {
        if (tv_no_more != null && !TextUtils.isEmpty(text))
            tv_no_more.setText(text);
    }

    public FooterViewHolder(Context context) {
        super(UIUtil.inflate(context, R.layout.holder_footer));
    }

    @Override
    public synchronized void bind(Integer data) {
        if (mCurrentState == data) {
            return;
        }

        mCurrentState = data;

        mRootView.setVisibility(View.VISIBLE);

        loadingView.setVisibility(data == HAS_MORE ? View.VISIBLE : View.INVISIBLE);
        noMoreView.setVisibility(data == NO_MORE ? View.VISIBLE : View.INVISIBLE);
        errorView.setVisibility(data == ERROR ? View.VISIBLE : View.INVISIBLE);
    }

    public void setOnErrorClickListener(View.OnClickListener listener) {
        errorView.setOnClickListener(listener);
    }

    public void setInvisible() {
        mRootView.setVisibility(View.INVISIBLE);
    }

    public void setGone() {
        mRootView.setVisibility(View.GONE);
    }


    public FrameLayout getRootView() {
        return mRootView;
    }

    public boolean isNoMore() {
        return mCurrentState == NO_MORE;
    }

    public boolean isError() {
        return mCurrentState == ERROR;
    }

    public boolean isHasLoadMore() {
        return mCurrentState == HAS_MORE;
    }

    public void setNoMoreView(View view, LinearLayout.LayoutParams params) {
        noMoreView.removeAllViews();
        noMoreView.addView(view, params);
    }


}
