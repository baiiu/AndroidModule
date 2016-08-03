package com.baiiu.myapplication.module.ultraPtr;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.baiiu.myapplication.R;
import com.baiiu.myapplication.module.ultraPtr.base.FooterViewHolder;
import com.baiiu.myapplication.module.ultraPtr.base.LoadingFrameRecyclerAdapter;
import com.baiiu.myapplication.module.ultraPtr.base.LoadingMoreScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * auther: baiiu
 * time: 16/8/1 01 22:13
 * description:
 */
public class PtrPageFragment extends Fragment implements LoadingMoreScrollListener.OnLoadingMoreListener, PtrHandler {

    private PtrClassicFrameLayout mPtr;
    private RecyclerView mRecyclerView;
    private LoadingMoreScrollListener mLoadingMoreScrollListener;
    private SimpleTextAdapterM mAdapter;
    private LoadingFrameRecyclerAdapter loadingFrameRecyclerAdapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ptr, container, false);

        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mAdapter = new SimpleTextAdapterM(getContext(), 0);
        loadingFrameRecyclerAdapter = new LoadingFrameRecyclerAdapter(getContext(), mAdapter);


        mAdapter.setOnErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                loadingMore();
            }
        });
        mRecyclerView.setAdapter(loadingFrameRecyclerAdapter);

        //下拉刷新
        mPtr.setPtrHandler(this);
        //上啦加载
        mLoadingMoreScrollListener = new LoadingMoreScrollListener();
        mLoadingMoreScrollListener.setOnLoadingMoreListener(this);
        mRecyclerView.addOnScrollListener(mLoadingMoreScrollListener);

        mPtr.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= 16) {
                            mPtr.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            mPtr.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }

                        mPtr.autoRefresh();
                    }
                });

        //SwipeRefreshLayout使用方式
        //final SwipeRefreshLayout s = new SwipeRefreshLayout(getContext());
        //s.setRefreshing(true);
        //s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //    @Override public void onRefresh() {
        //        s.setRefreshing(false);//即onRefreshComplete()
        //    }
        //});

        return view;
    }

    @Override public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override public void run() {
                frame.refreshComplete();

                //testEmpty();
                //testError();
                ok();

            }
        }, 1000);
    }

    private void ok() {
        mAdapter.setCount(20);
        mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
        loadingFrameRecyclerAdapter.showContent();
    }

    private void testEmpty() {
        loadingFrameRecyclerAdapter.setEmpty(true);
        loadingFrameRecyclerAdapter.setEmptyText("空的");
        loadingFrameRecyclerAdapter.notifyItemChanged(0);
    }


    private void testError() {
        loadingFrameRecyclerAdapter.setError(true);
        loadingFrameRecyclerAdapter.setErrorText("哈哈错误了");
        loadingFrameRecyclerAdapter.setOnErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        loadingFrameRecyclerAdapter.notifyItemChanged(0);
    }


    private int time;

    @Override public void loadingMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override public void run() {

                loadingFrameRecyclerAdapter.notifyItemRangeInserted(mAdapter.getInnerItemCount(), 20);
                mAdapter.setCount(mAdapter.getInnerItemCount() + 20);

                ++time;
                if (time == 2) {
                    mAdapter.bindFooter(FooterViewHolder.ERROR);
                } else if (time == 4) {
                    mAdapter.bindFooter(FooterViewHolder.NO_MORE);
                } else {
                    mLoadingMoreScrollListener.onLoadMoreComplete();
                    mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                }

            }
        }, 1000);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

}
