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
import com.baiiu.myapplication.R;
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
    //private FooterDecoratorAdapter footerDecoratorAdapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ptr, container, false);

        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mAdapter = new SimpleTextAdapterM(getContext(), 0);
        mAdapter.setOnErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                loadingMore();
            }
        });
        //footerDecoratorAdapter = new FooterDecoratorAdapter(getContext(), mAdapter);
        mRecyclerView.setAdapter(mAdapter);

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
                mAdapter.setCount(20);
                //footerDecoratorAdapter.notifyDataSetChanged();
                mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
            }
        }, 1000);
    }

    private int time;

    @Override public void loadingMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override public void run() {
                mAdapter.setCount(mAdapter.getRealItemCount() + 20);
                ++time;
                if (time == 2) {
                    mAdapter.bindFooter(FooterViewHolder.ERROR);
                } else if (time == 4) {
                    mAdapter.bindFooter(FooterViewHolder.NO_MORE);
                } else {
                    mLoadingMoreScrollListener.onLoadMoreComplete();
                    mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                }
                //footerDecoratorAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

}
