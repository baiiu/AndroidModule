package com.baiiu.myapplication.module.ultraPtr.sthElse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.baiiu.myapplication.module.ultraPtr.base.FooterViewHolder;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/8/3 10:08
 * description:
 */
public class FooterDecoratorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = -1;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private Context mContext;
    private FooterViewHolder footerViewHolder;

    public FooterDecoratorAdapter(Context context, RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return getFooterHolder();
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            return;
        }

        mAdapter.onBindViewHolder(holder, position);
    }

    @Override public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override public int getItemViewType(int position) {
        if (position >= mAdapter.getItemCount()) {
            return TYPE_FOOTER;
        }
        return mAdapter.getItemViewType(position);
    }

    public FooterViewHolder getFooterHolder() {
        if (footerViewHolder == null) {
            footerViewHolder = new FooterViewHolder(mContext);
            //footerViewHolder.getRootView().setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        }
        return footerViewHolder;
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
}
