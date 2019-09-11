package com.baiiu.myapplication.module.ultraPtr.sthElse;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.baiiu.myapplication.module.ultraPtr.base.BaseViewHolder;
import com.baiiu.myapplication.module.ultraPtr.base.LoadFrameLayout;

/**
 * auther: baiiu
 * time: 16/8/3 03 21:52
 * description: 使用装饰模式,并不推荐使用.需要同时操作两个adapter来展示数据
 */
public class LoadingFrameRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int TYPE_FRAME_LOADING = -2;

    private Context mContext;
    private RecyclerView.Adapter<BaseViewHolder> mAdapter;

    private boolean isLoading = true;
    private boolean isEmpty = false;
    private boolean isError = false;
    private String emptyText;
    private String errorText;
    private View.OnClickListener errorClickListener;


    public LoadingFrameRecyclerAdapter(Context context, RecyclerView.Adapter<BaseViewHolder> mAdapter) {
        this.mContext = context;
        this.mAdapter = mAdapter;
        notifyDataSetChanged();
    }

    public LoadingFrameRecyclerAdapter(Context context, RecyclerView.Adapter<BaseViewHolder> mAdapter,
            boolean isLoading) {
        this.mContext = context;
        this.mAdapter = mAdapter;
        this.isLoading = isLoading;
        notifyDataSetChanged();
    }

    public void showContent() {
        isLoading = false;
        isError = false;
        isEmpty = false;
        notifyDataSetChanged();
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
        isError = false;
        isLoading = false;
    }

    public void setError(boolean error) {
        isError = error;
        isEmpty = false;
        isLoading = false;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setOnErrorClickListener(View.OnClickListener listener) {
        this.errorClickListener = listener;
        notifyItemChanged(0);
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FRAME_LOADING) {
            return new LoadingFrameViewHolder(mContext, parent);
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof LoadingFrameViewHolder) {
            LoadingFrameViewHolder frameViewHolder = (LoadingFrameViewHolder) holder;

            if (isLoading) frameViewHolder.bind(LoadFrameLayout.LOADING);
            if (isEmpty) frameViewHolder.bind(LoadFrameLayout.EMPTY);
            if (isError) frameViewHolder.bind(LoadFrameLayout.ERROR);

            frameViewHolder.setErrorText(errorText);
            frameViewHolder.setEmptyText(emptyText);
            frameViewHolder.setOnErrorClickListener(errorClickListener);
            return;
        }

        mAdapter.onBindViewHolder(holder, position);
    }

    @Override public int getItemCount() {
        if (isLoading || isEmpty || isError) {
            return 1;
        }

        return mAdapter.getItemCount();
    }

    @Override public int getItemViewType(int position) {
        if (isLoading || isEmpty || isError) {
            return TYPE_FRAME_LOADING;
        }

        return mAdapter.getItemViewType(position);
    }
}
