package com.baiiu.myapplication.module.ultraPtr;

import android.content.Context;
import android.view.ViewGroup;
import com.baiiu.myapplication.module.fastscrooll.SimpleTextViewHolder;
import com.baiiu.myapplication.module.ultraPtr.base.BaseViewHolder;
import com.baiiu.myapplication.module.ultraPtr.base.FooterRecyclerAdapter;

/**
 * author: baiiu
 * date: on 16/7/1 19:47
 * description:
 */
class SimpleTextAdapterM extends FooterRecyclerAdapter {

    private Context mContext;
    private int mCount;

    public SimpleTextAdapterM(Context context, int count) {
        mContext = context;
        mCount = count;
    }

    public Context getContext() {
        return mContext;
    }

    @Override public int getItemCount() {
        if (mCount == 0) {
            return 0;
        }

        return super.getItemCount();
    }

    @Override protected int getInnerItemCount() {
        return mCount;
    }

    @Override protected BaseViewHolder onCreateInnerViewHolder(ViewGroup parent, int viewType) {
        return new SimpleTextViewHolder(mContext, parent);
    }

    @Override protected void onBindInnerViewHolder(BaseViewHolder holder, int position) {
        holder.bind(position);
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

}
