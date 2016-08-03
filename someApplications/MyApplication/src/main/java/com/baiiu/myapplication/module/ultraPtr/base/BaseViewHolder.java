package com.baiiu.myapplication.module.ultraPtr.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.baiiu.myapplication.util.UIUtil;

/**
 * Created by baiiu on 15/11/18.
 * BaseViewHolder
 */
public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {
    public OnClickListener mListener;
    public Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        this(context, layoutId, parent, null);
    }

    public BaseViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent, OnClickListener listener) {
        this(context, layoutId, parent, listener, true);
    }

    public BaseViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent, OnClickListener listener,
            boolean allClick) {
        super(UIUtil.inflate(context, layoutId, parent, false));
        this.mContext = context;

        if (itemView != null) {
            ButterKnife.bind(this, itemView);
            
            if (listener != null) {
                this.mListener = listener;

                if (allClick) {
                    itemView.setOnClickListener(listener);
                }
            }
        }
    }

    public abstract void bind(E data);
}
