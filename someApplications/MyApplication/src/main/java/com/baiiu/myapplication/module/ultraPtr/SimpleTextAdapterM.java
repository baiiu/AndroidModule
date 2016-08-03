package com.baiiu.myapplication.module.ultraPtr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.baiiu.myapplication.module.fastscrooll.SimpleTextViewHolder;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/1 19:47
 * description:
 */
class SimpleTextAdapterM extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_FOOTER = -1;

    private Context mContext;
    private int mCount;
    private FooterViewHolder footerViewHolder;

    public SimpleTextAdapterM(Context context, int count) {
        mContext = context;
        mCount = count;
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return getFooterHolder();
        }

        return new SimpleTextViewHolder(mContext, parent);
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            return;
        }

        holder.bind(position);
    }

    @Override public int getItemCount() {
        if (mCount == 0) {
            return 0;
        }
        return mCount + 1;
    }

    public int getRealItemCount() {
        return mCount;
    }

    @Override public int getItemViewType(int position) {
        if (position >= getItemCount() - 1) {
            return TYPE_FOOTER;
        }

        return 0;
    }

    public FooterViewHolder getFooterHolder() {
        if (footerViewHolder == null) {
            footerViewHolder = new FooterViewHolder(mContext);
            footerViewHolder.getRootView().setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        }
        return footerViewHolder;
    }

    public void setCount(int mCount) {
        if (this.mCount == 0) {
            //下拉刷新
            this.mCount = mCount;
            notifyDataSetChanged();
        } else {
            //上拉加载
            notifyItemRangeInserted(this.mCount, 20);
            this.mCount = mCount;
        }
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
