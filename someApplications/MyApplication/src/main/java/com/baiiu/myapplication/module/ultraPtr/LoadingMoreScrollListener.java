package com.baiiu.myapplication.module.ultraPtr;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.baiiu.myapplication.util.LogUtil;

/**
 * author: baiiu
 * date: on 16/7/1 20:06
 * description:
 */
public class LoadingMoreScrollListener extends RecyclerView.OnScrollListener {
    private boolean isLoading = true;
    private OnLoadingMoreListener mListener;

    /**
     * 最后第几个可见时开始加载数据
     */
    private static final int COUNT_TO_LOAD_MORE = 3;

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LogUtil.d("onScrolled");
        if (!isLoading) {
            return;
        }

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();


        //最后一个可见时加载数据
        if (dy > 0 && (lastVisibleItemPosition + COUNT_TO_LOAD_MORE >= itemCount)) {
            isLoading = false;
            if (mListener != null) {
                mListener.loadingMore();
            }
        }
    }

    /**
     * 数据加载更多完成后一定要设置为true
     */
    public void onLoadmoreComplete() {
        setLoading(true);
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
    }


    public interface OnLoadingMoreListener {
        void loadingMore();
    }

    public void setOnLoadingMoreListener(OnLoadingMoreListener loadingMoreListener) {
        this.mListener = loadingMoreListener;
    }
}