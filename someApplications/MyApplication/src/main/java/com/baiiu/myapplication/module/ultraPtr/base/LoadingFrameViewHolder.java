package com.baiiu.myapplication.module.ultraPtr.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.baiiu.myapplication.R;

/**
 * auther: baiiu
 * time: 16/8/3 03 21:58
 * description:
 */
public class LoadingFrameViewHolder extends BaseViewHolder<Integer> {
    public LoadingFrameViewHolder(Context mContext, ViewGroup parent) {
        super(mContext, R.layout.holder_frame_loading, parent);
    }

    @Override public void bind(@LoadFrameLayout.LoadFrameState Integer data) {
        ((LoadFrameLayout) itemView).bind(data);
    }

    public void setEmptyText(String emptyText) {
        ((LoadFrameLayout) itemView).setEmptyText(emptyText);
    }

    public void setErrorText(String errorText) {
        ((LoadFrameLayout) itemView).setErrorText(errorText);

    }

    public void setOnErrorClickListener(View.OnClickListener listener) {
        ((LoadFrameLayout) itemView).setOnErrorClickListener(listener);
    }

}
