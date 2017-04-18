package com.baiiu.toucheventstudy.floatingActionButtonBehavior;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * author: baiiu
 * date: on 16/7/1 19:47
 * description:
 */
public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextViewHolder> {

    private Context mContext;
    private int mCount;

    public SimpleTextAdapter(Context context, int count) {
        mContext = context;
        mCount = count;
    }

    @Override public SimpleTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleTextViewHolder(mContext, parent);
    }

    @Override public void onBindViewHolder(SimpleTextViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override public int getItemCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        notifyItemRangeInserted(this.mCount, 20);
        this.mCount = mCount;
    }

    public void addOne(int position) {
        notifyItemInserted(position);
        ++this.mCount;
    }

    public void deleteOne(int position) {
        notifyItemRemoved(position);
        --this.mCount;
    }

}
