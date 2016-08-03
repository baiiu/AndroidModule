package com.baiiu.myapplication.module.ultraPtr.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.baiiu.myapplication.util.UIUtil;
import java.util.List;

/**
 * auther: baiiu
 * time: 16/8/3 03 21:29
 * description:
 */
public abstract class FooterRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = -1;
    private FooterViewHolder mFooterViewHolder;

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return getFooterHolder();
        }

        return onCreateInnerViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            return;
        }

        onBindInnerViewHolder(holder, position);
    }


    @Override public int getItemCount() {
        return getInnerItemCount() + 1;
    }


    @Override public int getItemViewType(int position) {
        if (position >= getInnerItemCount()) {
            return TYPE_FOOTER;
        }

        return getInnerViewType(position);
    }

    protected int getInnerViewType(int position) {
        return super.getItemViewType(position);
    }

    protected abstract int getInnerItemCount();

    protected abstract BaseViewHolder onCreateInnerViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindInnerViewHolder(BaseViewHolder holder, int position);


    /*
        footer 一系列方法
     */

    public FooterViewHolder getFooterHolder() {
        if (mFooterViewHolder == null) {
            mFooterViewHolder = new FooterViewHolder(UIUtil.getContext());
            mFooterViewHolder.getRootView()
                    .setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        }

        return mFooterViewHolder;
    }

    public void bindFooter(@FooterViewHolder.FooterState int state) {
        getFooterHolder().bind(state);
    }

    public void bindFooter(List<?> list) {
        if (list == null) {
            getFooterHolder().bind(FooterViewHolder.ERROR);
        } else if (list.size() < 20) {
            getFooterHolder().bind(FooterViewHolder.NO_MORE);
        } else {
            getFooterHolder().bind(FooterViewHolder.HAS_MORE);
        }
    }

    public void setOnErrorClickListener(View.OnClickListener onClickListener) {
        getFooterHolder().setOnErrorClickListener(onClickListener);
    }
}
